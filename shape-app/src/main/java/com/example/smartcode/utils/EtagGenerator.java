package com.example.smartcode.utils;

import com.example.smartcode.common.Taggable;
import com.example.smartcode.exception.InvalidEtagException;

public interface EtagGenerator {

    String generateETag(Taggable taggable);

    void verifyETag(Taggable entity) throws InvalidEtagException;

}
