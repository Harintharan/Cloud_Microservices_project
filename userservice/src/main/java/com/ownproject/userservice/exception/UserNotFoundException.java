package com.ownproject.userservice.exception;

//public class UserException extends Exception{
//
//    public UserException(String message){
//        super(message);
//    }
//}



public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
