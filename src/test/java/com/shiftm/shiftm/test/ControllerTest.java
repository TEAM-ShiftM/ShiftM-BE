package com.shiftm.shiftm.test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shiftm.shiftm.ShiftMApplication;
import com.shiftm.shiftm.test.setup.domain.MemberSetUp;

@Transactional
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ShiftMApplication.class)
public class ControllerTest {
	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

	@Autowired
	protected ResourceLoader resourceLoader;

	@Autowired
	protected MemberSetUp memberSetUp;

	protected <T> T readJson(final String path, Class<T> clazz) throws IOException {
		final InputStream json = resourceLoader.getResource(path).getInputStream();
		return objectMapper.readValue(json, clazz);
	}

	protected String readJson(final String path) throws IOException {
		final InputStream json = resourceLoader.getResource(path).getInputStream();
		return StreamUtils.copyToString(json, StandardCharsets.UTF_8);
	}
}
