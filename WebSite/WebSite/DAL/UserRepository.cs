﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using NFCShoppingWebSite.Access_Data;

namespace NFCShoppingWebSite.DAL
{
    public class UserRepository : IUserRepository
    {
        private ShopEntities mContext = new ShopEntities();
        private bool mIsDisposed = false; 

        #region  IUserRepository接口具体实现

        public IEnumerable<User> GetUsers()
        {
            return mContext.Users.ToList();
        }

        public void InsertUser(User user, bool isImmediateSave)
        {
            try
            {
                mContext.Users.AddObject(user);

                if (isImmediateSave)
                {
                    mContext.SaveChanges();
                }
            }
            catch (Exception e)
            {
                throw e;
            }
        }

        public void DeleteUser(User user, bool isImmediateSave)
        {
            try
            {
                mContext.Users.Attach(user);
                mContext.Users.DeleteObject(user);

                if (isImmediateSave)
                {
                    mContext.SaveChanges();
                }
            }
            catch (Exception e)
            {
                throw e;
            }

        }

        public void UpdateUser(User user, User origUser, bool isImmediateSave)
        {
            try
            {
                mContext.Users.Attach(origUser);
                mContext.Users.ApplyCurrentValues( user);

                if (isImmediateSave)
                {
                    mContext.SaveChanges();
                }
            }
            catch(Exception e)
            {
                throw e;
            }

        }

        #endregion

        protected virtual void Dispose(bool disposing) 
        { 
            if (!this.mIsDisposed) 
            { 
                if (disposing) 
                {
                    mContext.SaveChanges();
                    mContext.Dispose();
                } 
            } 
            this.mIsDisposed = true;
        } 

        public void Dispose()
        {
            Dispose(true); 
            GC.SuppressFinalize(this); 
        }
    }
}