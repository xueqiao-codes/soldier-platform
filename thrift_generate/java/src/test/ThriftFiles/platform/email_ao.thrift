/**
  * email ao 
  */
namespace java org.soldier.platform.mail.ao

include "../comm.thrift"

enum ContentType {
	Text,
	Html
}

struct Email {
	1:required list<string> receivors; // 接收人列表
	2:optional list<string> cc;  // 抄送人列表
	3:optional list<string> bcc;  // 秘密抄送人列表
	4:required string subject;   // 主题
	5:required string content;   // 内容
	6:required ContentType contentType; // 内容类型 
}

service(24) EmailAo {
	void 1:sendEmail(1:comm.PlatformArgs platformArgs, 2:Email email)
		throws (1:comm.ErrorInfo err);
}
