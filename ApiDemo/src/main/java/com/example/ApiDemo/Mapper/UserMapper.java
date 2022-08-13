package com.example.ApiDemo.Mapper;

import com.example.ApiDemo.Enity.User;

public interface UserMapper {
   public void InsertData( User user);
   public User getData(String userName);
}
