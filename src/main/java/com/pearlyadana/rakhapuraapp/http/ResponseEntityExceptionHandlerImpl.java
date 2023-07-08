package com.pearlyadana.rakhapuraapp.http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestControllerAdvice
public class ResponseEntityExceptionHandlerImpl extends ResponseEntityExceptionHandler {

	public static final Logger log = LoggerFactory.getLogger(ResponseEntityExceptionHandlerImpl.class);

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		final StringBuilder builder = new StringBuilder();
		builder.append(ex.getMethod());
		builder.append(" method is not supported for this request. Supported methods are ");
		ex.getSupportedHttpMethods().forEach(t -> builder.append(t).append(" "));

		Map<String, Object> body = new HashMap<String, Object>();
		body.put("timestamp", new Date());
		body.put("status", status.value());
		body.put("error", "Http request mehtod not supported");
		body.put("message", builder.toString());
		body.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());

		return handleExceptionInternal(ex, body, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		final StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(" "));

		Map<String, Object> body = new HashMap<String, Object>();
		body.put("timestamp", new Date());
		body.put("status", status.value());
		body.put("error", "Http media type not supported");
		body.put("message", builder.toString());
		body.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());

		return handleExceptionInternal(ex, body, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("timestamp", new Date());
		body.put("status", status.value());
		body.put("error", "Missing path variable");
		body.put("message", "Path variable " + ex.getParameter() + " is required");
		body.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());

		return handleExceptionInternal(ex, body, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		final String error = ex.getParameterName() + " parameter is missing";
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("timestamp", new Date());
		body.put("status", status.value());
		body.put("error", "Missing servlet request parameter");
		body.put("message", error);
		body.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());

		return handleExceptionInternal(ex, body, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, Object> body = new HashMap<String, Object>();
		body.put("timestamp", new Date());
		body.put("status", status.value());
		body.put("error", "Servlet request binding error");
		body.put("message", ex.getMessage());
		body.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());

		return handleExceptionInternal(ex, body, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("timestamp", new Date());
		body.put("status", status.value());
		body.put("error", "Conversion not supported");
		body.put("message", ex.getMessage());
		body.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());

		return handleExceptionInternal(ex, body, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		final String error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type "
				+ ex.getRequiredType();
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("timestamp", new Date());
		body.put("status", status.value());
		body.put("error", "Type mismatch");
		body.put("message", error);
		body.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());

		return handleExceptionInternal(ex, body, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("timestamp", new Date());
		body.put("status", status.value());
		body.put("error", "Http message not readable");
		body.put("message", ex.getMessage());
		body.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());

		return handleExceptionInternal(ex, body, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("timestamp", new Date());
		body.put("status", status.value());
		body.put("error", "Http message not writable");
		body.put("message", ex.getMessage());
		body.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());

		return handleExceptionInternal(ex, body, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		final List<String> errors = new ArrayList<String>();
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("timestamp", new Date());
		body.put("status", status.value());
		body.put("error", "Method argument not valid");
		body.put("message", errors);
		body.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());

		return handleExceptionInternal(ex, body, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		final String error = ex.getRequestPartName() + " part is missing";
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("timestamp", new Date());
		body.put("status", status.value());
		body.put("error", "Missing servlet request part");
		body.put("message", error);
		body.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());

		return handleExceptionInternal(ex, body, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {

		final List<String> errors = new ArrayList<String>();
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}

		Map<String, Object> body = new HashMap<String, Object>();
		body.put("timestamp", new Date());
		body.put("status", status.value());
		body.put("error", "Bind exception");
		body.put("message", errors);
		body.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());

		return handleExceptionInternal(ex, body, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

		Map<String, Object> body = new HashMap<String, Object>();
		body.put("timestamp", new Date());
		body.put("status", status.value());
		body.put("error", "No handler found");
		body.put("message", error);
		body.put("path", ex.getRequestURL());

		return handleExceptionInternal(ex, body, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, Object> body = new HashMap<String, Object>();
		body.put("timestamp", new Date());
		body.put("status", status.value());
		body.put("error", "Async request timeout");
		body.put("message", ex.getLocalizedMessage());
		body.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());

		return handleExceptionInternal(ex, body, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		// You cannot catch All RuntimeException from here... just for Http Related
		// Exceptions

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	// Custom Methods

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public void handleMethodArgumentTypeMismatch(final HttpServletResponse response,
			final MethodArgumentTypeMismatchException ex, final WebRequest request) {

		final String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();

		Map<String, Object> body = new HashMap<String, Object>();
		body.put("timestamp", new Date());
		body.put("status", HttpStatus.BAD_REQUEST.value());
		body.put("error", "Method Argument Type Mismatch");
		body.put("message", error);
		body.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());

		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
		try {
			new Gson().toJson(body, new TypeReference<Map<String, Object>>() {
			}.getType(), response.getWriter());
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public void handleConstraintViolation(final HttpServletResponse response, final ConstraintViolationException ex,
			final WebRequest request) {

		final List<String> errors = new ArrayList<String>();
		for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			errors.add(violation.getPropertyPath() + ": "
					+ violation.getMessage());
		}

		Map<String, Object> body = new HashMap<String, Object>();
		body.put("timestamp", new Date());
		body.put("status", HttpStatus.BAD_REQUEST.value());
		body.put("error", "Constraint violation error");
		body.put("message", errors);
		body.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());

		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
		try {
			new Gson().toJson(body, new TypeReference<Map<String, Object>>() {
			}.getType(), response.getWriter());
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@ExceptionHandler({ NumberFormatException.class })
	public void handleAll(final HttpServletResponse response, final NumberFormatException ex,
			final WebRequest request) {
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("timestamp", new Date());
		body.put("status", HttpStatus.BAD_REQUEST.value());
		body.put("error", "Bad request");
		body.put("message", "Invalid number");
		body.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());

		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
		try {
			new Gson().toJson(body, new TypeReference<Map<String, Object>>() {
			}.getType(), response.getWriter());
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@ExceptionHandler({ RuntimeException.class })
	public void handleAll(final HttpServletResponse response, final RuntimeException ex, final WebRequest request) {

		log.error("<<<<<<<<<<Unexpected Exception>>>>>>>>>", ex);

		Map<String, Object> body = new HashMap<String, Object>();
		body.put("timestamp", new Date());
		body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		body.put("error", "Internal server error");
		body.put("message", "Internal server error occured, please see in log file");
		body.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());

		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
		try {
			new Gson().toJson(body, new TypeReference<Map<String, Object>>() {
			}.getType(), response.getWriter());
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@ExceptionHandler(org.springframework.web.multipart.MaxUploadSizeExceededException.class)
	public void uploadFileMaxSizeExceededHandler(final HttpServletResponse response, final RuntimeException ex,
			final WebRequest request) {
		log.error("<<<<<<<<<< Uploaded File Size Exceeded Exception>>>>>>>>>", ex);

		Map<String, Object> body = new HashMap<String, Object>();
		body.put("timestamp", new Date());
		body.put("status", HttpStatus.BAD_REQUEST.value());
		body.put("error", "Bad request");
		body.put("message", "Maximum uploaded files' size exceeded");
		body.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());

		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
		try {
			new Gson().toJson(body, new TypeReference<Map<String, Object>>() {
			}.getType(), response.getWriter());
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@ExceptionHandler({ AccessDeniedException.class })
	public void handleAccessDenied(final HttpServletResponse response, final RuntimeException ex, final WebRequest request) {

		log.error("<<<<<<<<<< Access Denied Exception>>>>>>>>>", ex);

		Map<String, Object> body = new HashMap<String, Object>();
		body.put("timestamp", new Date());
		body.put("status", HttpStatus.FORBIDDEN.value());
		body.put("error", "Access Denied");
		body.put("message", "Access Denied");
		body.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());

		response.setStatus(HttpStatus.FORBIDDEN.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
		try {
			new Gson().toJson(body, new TypeReference<Map<String, Object>>() {
			}.getType(), response.getWriter());
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
