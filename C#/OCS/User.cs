using System;

namespace OCS
{
    [Serializable]
    public class User
    {
        private string userId;
        private string userPasswd;
        private Boolean rememberPasswd;
        private string userName;

        public string UserId
        {
            get => userId;
            set => userId = value;
        }

        public bool RememberPasswd
        {
            get => rememberPasswd;
            set => rememberPasswd = value;
        }

        public string UserName
        {
            get => userName;
            set => userName = value;
        }

        public string UserPasswd
        {
            get => userPasswd;
            set => userPasswd = value;
        }
    }
}