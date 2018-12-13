1.BaseActivity 继承 AppCompatActivity  做了一个调整颜色的基类，让所以更换背景的活动都继承这个基类
颜色设定在 values里面的styles.xml

2.LoadActivity 加载页面的一个活动，采用播放视频的方式

3.MActivity 基本页面，有一个viewpager来显示页面，

可以美化这个页面，让这个页面更美观。

4.MainActivity 
没有使用这个
这个是开始测试的页面。
其中按钮 md_900 录制视频，数据存放在 
/storage/emulated/0/Android/data/com.example.qq1296821114/files

5.Mp4MuxerActivity 
没有使用这个
录制视频的活动，是一个开源项目，我把库文件放在了applib当中

6.ReadActivity
没有使用这个
这个是测试的时候使用的活动，用来显示数据

7.ShareActivity
没有使用这个
这个是测试的时候使用的活动，用来显示数据

8.com.example.qq1296821114.Data.Bean.Letter
实体化这个信，是让所有的关于信的逻辑放在这里。

9.com.example.qq1296821114.Data.DataBase.DBOpenHelper
用来帮助建立数据库，提供建表代码

10.com.example.qq1296821114.Data.DataBase.MyDB
数据实体，让所有关于数据的操作都放在这里

11.com.example.qq1296821114.Fragment.SendLetterFragment
写信的界面，是一个碎片，放在主类的 view pager当中
使用了一个开源项目，文本编辑器。
可以搜索 'jp.wasabeef:richeditor-android:1.2.2'，资料不多。。

12.com.example.qq1296821114.Fragment.SquareFragment
查看数据的页面，点击上面的黑色按钮加载页面。

13.com.example.qq1296821114.Utils.Adapter.FragmentAdapter
view pager的适配器，用来管理 前面的发送数据和显示数据的页面

14.com.example.qq1296821114.Utils.Adapter.SquareRecycleAdapter
查看数据页面里面的RecyclerView的数据适配器

15.com.example.qq1296821114.Utils.DBUtil
让所有关于数据的操作都调用这个里面的静态方法

16.com.example.qq1296821114.Utils.HttpUtil
使用开源项目okhttp，资料比较多，用来和服务器 通信数据

17.com.example.qq1296821114.Utils.RealPathFromUriUtils
用来帮助获取本地图片的uri

18.com.example.qq1296821114.Utils.Util
有设置颜色的方法
获取颜色，弹出一个toast。检查权限，获取权限
获取实体信当中的 图片列表
压缩图片，这个也是一个开源项目。
存储bitmap到本地。从本地读取图片
bitmap 加密到字符串， 字符串解密。使用base64

19.com.example.qq1296821114.View.Dialog.ChooseColorDialog
选择颜色的对话框，调用他的show方法就可以显示了

20.com.example.qq1296821114.View.Dialog.Test_Dialog
测试对话框，用来显示图片

21.com.example.qq1296821114.View.view.CameraPreviewTextureView
视频录制，开源项目的代码，用来显示录像的界面

22.com.example.qq1296821114.View.view.KeyboardLayout
用来检测是否有 软键盘弹出的布局，继承线性布局

23.com.example.qq1296821114.View.view.MyVideoView
用来播放视频的view，播放的视频放在 assets当中

24.com.example.qq1296821114.View.view.MyViewPager
让view pager不能左右滑动


图标使用 阿里巴巴图标网找的，导入矢量图，可以百度了解

applib当中是关于录制视频的代码。
整体就是把数据分成 音频，和图片的数据流，有不同的存储的帧率。
已经忘记了