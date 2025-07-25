package com.simply.ai.services;

import com.simply.exception.ProductException;
import com.simply.response.ApiResponse;

public interface AiChatBotService {

    ApiResponse aiChatBot(String prompt,Long productId,Long userId) throws ProductException;
}
