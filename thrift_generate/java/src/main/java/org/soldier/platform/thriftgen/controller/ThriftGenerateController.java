package org.soldier.platform.thriftgen.controller;

import org.soldier.platform.thriftgen.Constants;
import org.soldier.platform.thriftgen.model.GenerateResult;
import org.soldier.platform.thriftgen.model.ThriftFileInfo;
import org.soldier.platform.thriftgen.service.ThriftGenerateService;
import org.soldier.platform.thriftgen.util.FileUtil;
import org.soldier.platform.thriftgen.util.TextUtil;
import org.soldier.platform.thriftgen.vo.BaseRspVo;
import org.soldier.platform.thriftgen.vo.CommonListRspVo;
import org.soldier.platform.thriftgen.vo.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ThriftGenerateController {

    private static final List<String> sSupportLangs = new ArrayList<String>() {{
        add("java");add("py");add(Constants.JAVA_WITH_PYTEST);
        add("csharp");add("cpp");add("js");
    }};

    @Autowired
    ThriftGenerateService mThriftGenService;

    @RequestMapping(value = "/getAllFiles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseRspVo> getAllFiles() {
        return getAllFiles("");
    }

    @RequestMapping(value = "/getAllFiles/{folderId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseRspVo> getAllFiles(@PathVariable String folderId) {
        List<ThriftFileInfo> infos = mThriftGenService.getAllFilesInFolder(folderId);
        return  responseThriftList(infos);
    }

    @RequestMapping(value = "/searchFiles/{keyword}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseRspVo> filterFiles(@PathVariable("keyword") String keyword) {
        List<ThriftFileInfo> infos = mThriftGenService.searchFiles(keyword);
        return  responseThriftList(infos);
    }

    @RequestMapping(value = "/getFile/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseRspVo> getFile(@PathVariable("id") String id) {
        ThriftFileInfo info = mThriftGenService.getFile(id);
        if (info != null) {
            BaseRspVo rsp = new BaseRspVo();
            rsp.setRetCode(ResultCode.C_SUCCESS);
            rsp.setData(info);
            return new ResponseEntity<BaseRspVo>(rsp, HttpStatus.OK);
        } else {
            return new ResponseEntity<BaseRspVo>(new BaseRspVo(ResultCode.C_RESOURCE_NONEXIST), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/getFileContent/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseRspVo> getFileContent(@PathVariable("id") String id) {
        ThriftFileInfo info = mThriftGenService.getFile(id);
        if (info != null) {
            String fileContent = FileUtil.getFileContentByLine(info.path());
            BaseRspVo rsp = new BaseRspVo();
            rsp.setData(fileContent);
            return new ResponseEntity<BaseRspVo>(rsp, HttpStatus.OK);
        } else {
            return new ResponseEntity<BaseRspVo>(new BaseRspVo(ResultCode.C_RESOURCE_NONEXIST), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/generate/{lang}/{ids}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<BaseRspVo> generate(@PathVariable("lang") String lang, @PathVariable("ids") String idArray) {
        String[] ids = idArray.split(",");
        if (ids != null && ids.length > 0 && sSupportLangs.contains(lang)) {
            ArrayList<String> idList = new ArrayList<String>();
            for (String id: ids) {
                idList.add(id);
            }

            GenerateResult result  = mThriftGenService.generate(idList, lang);
            if (result.isSuccess()) {
                BaseRspVo rsp = new BaseRspVo();
                rsp.setData(result.getZipFileId());
                return new ResponseEntity<BaseRspVo>(rsp, HttpStatus.OK);
            } else {
                BaseRspVo rsp = new BaseRspVo(ResultCode.C_FAIL);
                rsp.setData(result.getError());
                return new ResponseEntity<BaseRspVo>(rsp, HttpStatus.OK);
            }
        }

        return new ResponseEntity<BaseRspVo>(new BaseRspVo(ResultCode.C_BAD_PARAM), HttpStatus.OK);
    }

    @RequestMapping(value = "/downloadResultZip/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> downloadResultZip(@PathVariable("id") String id) {
        if (!TextUtil.isEmpty(id)) {
            byte[] fileBytes = mThriftGenService.downloadResultZipById(id);
            if (fileBytes != null && fileBytes.length > 0) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentDispositionFormData("attachment", id + Constants.ZIP_SUFFIX);
                return new ResponseEntity<byte[]>(fileBytes,headers, HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<byte[]>(new BaseRspVo(ResultCode.C_BAD_PARAM).toString().getBytes(), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<BaseRspVo> responseThriftList(List<ThriftFileInfo> infos) {
        if (infos != null && infos.size() > 0) {
            CommonListRspVo cRsp = new CommonListRspVo();
            cRsp.setData(infos);
            cRsp.setTotal(infos.size());
            cRsp.setRetCode(ResultCode.C_SUCCESS);
            return new ResponseEntity<BaseRspVo>(cRsp, HttpStatus.OK);
        } else {
            return new ResponseEntity<BaseRspVo>(new BaseRspVo(ResultCode.C_RESOURCE_NONEXIST), HttpStatus.OK);
        }
    }
}
