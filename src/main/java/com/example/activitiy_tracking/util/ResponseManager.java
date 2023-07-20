package com.example.activitiy_tracking.util;

import com.example.activitiy_tracking.model.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ResponseManager<T> {
    public ApiResponse<T> success(T data){
        return new ApiResponse<T>((T) "Request Successful",true, data);
    }

    public ApiResponse<T> error(T errorMessage){
        return new ApiResponse<T>(errorMessage, false, null);
    }
}
