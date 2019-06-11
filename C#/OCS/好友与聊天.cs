using System;
using System.Data;
using System.IO;
using System.Net.Sockets;
using System.Windows.Forms;
using PMS;
using System.Net;
using System.Text;
using System.Threading;
using MySql.Data.MySqlClient;

namespace OCS
{
    public partial class 好友与聊天 : Form
    {
        private MySqlConnection mySqlConnection = SetConnection.mySqlConnection;
        private User user;
        Socket clientSocket = null;
        static Boolean isListen = true;
        Thread thDataFromServer;
        IPAddress ipadr;


        public 好友与聊天(User user)
        {
            InitializeComponent();
            this.user = user;
            ipadr = IPAddress.Loopback;
            setConnect();
        }

        private void 好友与聊天_Load(object sender, EventArgs e)
        {
            treeView1.LabelEdit = true;

            try
            {
                string cmd = "SELECT distinct userGroup  from relationshipPlus where userId=@userId;"
                             + "SELECT friendId AS friends,userGroup as myGroup,userName as userName,friendName as friendName from relationshipPlus  WHERE userId =@userId  union ALL SELECT userId as friends, friendGroup as myGroup,friendName as userName,userName as friendName  from relationshipPlus  WHERE friendId = @userId";

                MySqlCommand mySqlCommand = new MySqlCommand(cmd, mySqlConnection);
                mySqlCommand.Parameters.Add("@userId", MySqlDbType.Int16);
                mySqlCommand.Parameters["@userId"].Value = user.UserId;

                MySqlDataAdapter mySqlDataAdapter = new MySqlDataAdapter();
                mySqlDataAdapter.SelectCommand = mySqlCommand;

                DataSet dataSet = new DataSet();
                mySqlDataAdapter.Fill(dataSet, "relationshipPlus");
                foreach (DataRow dataRow in dataSet.Tables[0].Rows)
                {
                    TreeNode treeNode = new TreeNode();
                    treeNode.Text = dataRow["userGroup"].ToString();
                    treeView1.Nodes.Add(treeNode);
                    foreach (DataRow row in dataSet.Tables[1].Rows)
                    {
                        if (row["myGroup"].ToString().Equals(treeNode.Text))
                        {
                            treeNode.Nodes.Add(new TreeNode(row["friendName"] + "(" + row["friends"] + ")"));
                            break;
                        }
                    }
                }
            }
            catch (Exception exception)
            {
                Console.WriteLine(exception);
                throw;
            }
        }


        private void SendMessage()
        {
            if (String.IsNullOrWhiteSpace(textBox2.Text.Trim()))
            {
                MessageBox.Show("发送内容不能为空哦~");
                return;
            }

            if (clientSocket != null && clientSocket.Connected)
            {
                Byte[] bytesSend = Encoding.UTF8.GetBytes(textBox2.Text + "$");
                clientSocket.Send(bytesSend);
                textBox2.Text = "";
            }
            else
            {
                MessageBox.Show("未连接服务器或者服务器已停止，请联系管理员~");
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            SendMessage();
        }

        private void setConnect()
        {
            if (String.IsNullOrWhiteSpace(user.UserName))
            {
                MessageBox.Show("请设置用户名哦亲");
            }

            if (clientSocket == null || !clientSocket.Connected)
            {
                try
                {
                    clientSocket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
                    //参考网址： https://msdn.microsoft.com/zh-cn/library/6aeby4wt.aspx
                    // Socket.BeginConnect 方法 (String, Int32, AsyncCallback, Object)
                    //开始一个对远程主机连接的异步请求
                    /* string host,     远程主机名
                     * int port,        远程主机的端口
                     * AsyncCallback requestCallback,   一个 AsyncCallback 委托，它引用连接操作完成时要调用的方法，也是一个异步的操作
                     * object state     一个用户定义对象，其中包含连接操作的相关信息。 当操作完成时，此对象会被传递给 requestCallback 委托
                     */
                    //如果txtIP里面有值，就选择填入的IP作为服务器IP，不填的话就默认是本机的

                    ipadr = IPAddress.Loopback;

                    clientSocket.BeginConnect(ipadr, 8080, args =>
                    {
                        if (args.IsCompleted) //判断该异步操作是否执行完毕
                        {
                            Byte[] bytesSend = new Byte[4096];
                        if (user.UserName == null)
                            bytesSend = Encoding.UTF8.GetBytes(user.UserId + "$"); //用户名，这里是刚刚连接上时需要传过去
                        else
                            bytesSend = Encoding.UTF8.GetBytes(user.UserName + "$"); //用户名，这里是刚刚连接上时需要传过去

                            if (clientSocket != null && clientSocket.Connected)
                            {
                                clientSocket.Send(bytesSend);
                                textBox2.Focus(); //将焦点放在
                                thDataFromServer = new Thread(DataFromServer);
                                thDataFromServer.IsBackground = true;
                                thDataFromServer.Start();
                            }
                            else
                            {
                                MessageBox.Show("服务器已关闭");
                            }
                        }
                    }, null);
                }
                catch (SocketException ex)
                {
                    MessageBox.Show(ex.ToString());
                }
            }
            else
            {
                MessageBox.Show("你已经连接上服务器了");
            }
        }


        private void DataFromServer()
        {
            ShowMsg("Connected to the Chat Server...");
            isListen = true;
            try
            {
                while (isListen)
                {
                    Byte[] bytesFrom = new Byte[4096];
                    Int32 len = clientSocket.Receive(bytesFrom);

                    String dataFromClient = Encoding.UTF8.GetString(bytesFrom, 0, len);
                    if (!String.IsNullOrWhiteSpace(dataFromClient))
                    {
                        //如果收到服务器已经关闭的消息，那么就把客户端接口关了，免得出错，并在客户端界面上显示出来
                        if (dataFromClient.ToString().Length >= 17 &&
                            dataFromClient.ToString().Substring(0, 17).Equals("Server has closed"))
                        {
                            clientSocket.Close();
                            clientSocket = null;

                            textBox1.BeginInvoke(new Action(() =>
                            {
                                textBox1.Text += Environment.NewLine + "服务器已关闭";
                            }));

                            thDataFromServer.Abort(); //这一句必须放在最后，不然这个进程都关了后面的就不会执行了

                            return;
                        }


                        if (dataFromClient.StartsWith("#") && dataFromClient.EndsWith("#"))
                        {
                            String userName = dataFromClient.Substring(1, dataFromClient.Length - 2);
                            this.BeginInvoke(new Action(() =>
                            {
                                MessageBox.Show("用户名：[" + userName + "]已经存在，请尝试其他用户名并重试");
                            }));
                            isListen = false;
                            clientSocket.Send(Encoding.UTF8.GetBytes("$"));
                            clientSocket.Close();
                            clientSocket = null;
                        }
                        else
                        {
                            //txtName.Enabled = false;    //当用户名唯一时才禁止再次输入用户名
                            ShowMsg(dataFromClient);
                        }
                    }
                }
            }
            catch (SocketException ex)
            {
                isListen = false;
                if (clientSocket != null && clientSocket.Connected)
                {
                    //没有在客户端关闭连接，而是给服务器发送一个消息，在服务器端关闭连接
                    //这样可以将异常的处理放到服务器。客户端关闭会让客户端和服务器都抛异常
                    clientSocket.Send(Encoding.UTF8.GetBytes("$"));
                    MessageBox.Show(ex.ToString());
                }
            }
        }

        private void ShowMsg(String msg)
        {
            textBox1.BeginInvoke(new Action(() =>
            {
                textBox1.Text +=
                    Environment.NewLine + msg; // 在 Windows 环境中，C# 语言 Environment.NewLine == "\r\n" 结果为 true
                //txtReceiveMsg.ScrollToCaret();
            }));
        }
    }
}