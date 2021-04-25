package org.soldier.platform.admin.controller.json;

import java.util.regex.Pattern;

import org.apache.thrift.TException;
import org.soldier.platform.admin.controller.AjaxOpException;
import org.soldier.platform.admin.controller.CJsonAjaxOpController;
import org.soldier.platform.file_storage_info.dao.AccessAttribute;
import org.soldier.platform.file_storage_info.dao.QueryStorageInfoListOption;
import org.soldier.platform.file_storage_info.dao.StorageInfo;
import org.soldier.platform.file_storage_info.dao.StorageInfoList;
import org.soldier.platform.file_storage_info.dao.client.FileStorageInfoDaoStub;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import com.antiy.error_code.ErrorCodeOuter;

public class FileStorageOperation extends CJsonAjaxOpController {
	private static final String storageKeyRegular = "[a-zA-Z][a-zA-Z0-9_]{0,32}";
	private static final Pattern storageKeyPattern = Pattern.compile(storageKeyRegular);
	
	private String checkStorageKey() throws AjaxOpException {
		String storageKey = parameter("storageKey", "").trim();
		if (storageKey.isEmpty()) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"存储Key值必须指定");
		}
		if (!storageKeyPattern.matcher(storageKey).matches()) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"存储Key命名必须满足正则" + storageKeyRegular);
		}
		
		return storageKey;
	}
	
	private StorageInfo checkStorageInfo() throws AjaxOpException {
		StorageInfo info = new StorageInfo();
		info.setStorageKey(checkStorageKey());
		
		AccessAttribute accessAttribute = 
				AccessAttribute.valueOf(parameter("accessAttribute", "").trim());
		if (accessAttribute == null) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"请指定正确权限属性");
		}
		info.setAccessAttribute(accessAttribute);
		
		String desc = parameter("remark", "").trim();
		if (desc.length() < 6) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"请至少输入6个字符的备注");
		}
		info.setDesc(desc);
		
		return info;
	}
	
	
	@Override
	protected void doAdd() throws AjaxOpException, ErrorInfo, TException {
		StorageInfo info = checkStorageInfo();
		
		FileStorageInfoDaoStub stub = new FileStorageInfoDaoStub();
		
		QueryStorageInfoListOption option = new QueryStorageInfoListOption();
		option.setStorageKey(info.getStorageKey());
		StorageInfoList resultList = stub.queryStorageList(info.getStorageKey().hashCode(),
				2000, 0, 10, option);
		if (resultList.getTotalNum() > 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"已经存在对应的存储Key值");
		}
		
		stub.addStorage(info.getStorageKey().hashCode(), 2000, info);
	}

	@Override
	protected void doUpdate() throws AjaxOpException, ErrorInfo, TException {
		StorageInfo info = checkStorageInfo();
		
		FileStorageInfoDaoStub stub = new FileStorageInfoDaoStub();
		QueryStorageInfoListOption option = new QueryStorageInfoListOption();
		option.setStorageKey(info.getStorageKey());
		StorageInfoList resultList = stub.queryStorageList(info.getStorageKey().hashCode(),
				2000, 0, 10, option);
		if (resultList.getTotalNum() <= 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"不存在对应的存储Key值");
		}
		
		stub.updateStorage(info.getStorageKey().hashCode(), 2000, info);
	}
	
	protected void doDelete() throws AjaxOpException, ErrorInfo, TException {
		String storageKey = checkStorageKey();
		new FileStorageInfoDaoStub().deleteStorage(storageKey.hashCode(), 2000, storageKey);
	}
	
	
	public static void main(String[] args) {
		Pattern p = Pattern.compile(storageKeyRegular);
		System.out.println(p.matcher("galacard_user").matches());
		System.out.println(p.matcher("toy_user").matches());
		System.out.println(p.matcher("").matches());
		System.out.println(p.matcher("hehe123").matches());
		System.out.println(p.matcher("1a").matches());
		System.out.println(p.matcher("a13123123").matches());
		System.out.println(p.matcher("a123123123123123123123123").matches());
	}
}
