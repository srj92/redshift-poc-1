package com.mettl.poc.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.stereotype.Component;

@Component
public class GzipCompressor {

	public static void compressGZIP(File input, File output) throws IOException {
        try (GzipCompressorOutputStream out = new GzipCompressorOutputStream(new FileOutputStream(output))){
            IOUtils.copy(new FileInputStream(input), out);
        }
    }

    public static void decompressGZIP(File input, File output) throws IOException {
        try (GzipCompressorInputStream in = new GzipCompressorInputStream(new FileInputStream(input))){
            IOUtils.copy(in, new FileOutputStream(output));
        }
    }
	
}
