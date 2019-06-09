using System;
using System.Net;
using System.Net.Mail;

namespace OCS
{
    public class MailService
    {
        //生成6位数字和大写字母的验证码
        public static string createRandom(int codelengh)
        {
            int rep = 0;
            string str = string.Empty;
            long num2 = DateTime.Now.Ticks + rep;
            rep++;
            Random random = new Random((int) ((ulong) num2 & 0xffffffffL) | (int) (num2 >> rep));
            for (int i = 0; i < codelengh; i++)
            {
                char ch;
                int num = random.Next();
                if ((num % 2) == 0)
                {
                    ch = (char) (0x30 + ((ushort) (num % 10)));
                }
                else
                {
                    ch = (char) (0x41 + ((ushort) (num % 0x1a)));
                }

                str = str + ch;
            }

            return str;
        }

        public static string SendMail(string UserMail)
        {
            //实例化一个发送邮件类。
            MailMessage mailMessage = new MailMessage();
            //发件人邮箱地址，方法重载不同，可以根据需求自行选择。
            mailMessage.From = new MailAddress("kangyh@email.kangyh.top");
            //收件人邮箱地址。
            mailMessage.To.Add(new MailAddress(UserMail));
            //邮件标题。
            mailMessage.Subject = "这是你的验证码";
            string verificationcode = createRandom(6);
            //邮件内容。
            mailMessage.Body = "你的验证码是" + verificationcode;
            //实例化一个SmtpClient类。
            SmtpClient client = new SmtpClient();
            //在这里我使用的是qq邮箱，所以是smtp.qq.com，如果你使用的是126邮箱，那么就是smtp.126.com。
            client.Host = "smtpdm.aliyun.com";
            //使用安全加密连接。
            client.EnableSsl = true;
            //不和请求一块发送。
            client.UseDefaultCredentials = false;
            //验证发件人身份(发件人的邮箱，邮箱里的生成授权码);
            client.Credentials = new NetworkCredential("kangyh@email.kangyh.top", "KangYu1027");
            //发送
            client.Send(mailMessage);
            return verificationcode;
        }
    }
}