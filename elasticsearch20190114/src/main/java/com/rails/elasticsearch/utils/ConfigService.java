package com.rails.elasticsearch.utils;

public interface ConfigService {
	/**
	 * Return the property value with the given key, or {@code defaultValue} if the
	 * key doesn't exist.
	 *
	 * @param key
	 *            the property name
	 * @param defaultValue
	 *            the default value when key is not found or any error occurred
	 * @return the property value
	 */
	public String getProperty(String key, String defaultValue);

	/**
	 * Return the integer property value with the given key, or {@code defaultValue}
	 * if the key doesn't exist.
	 *
	 * @param key
	 *            the property name
	 * @param defaultValue
	 *            the default value when key is not found or any error occurred
	 * @return the property value as integer
	 */
	public Integer getIntProperty(String key, Integer defaultValue);

	/**
	 * Return the long property value with the given key, or {@code defaultValue} if
	 * the key doesn't exist.
	 *
	 * @param key
	 *            the property name
	 * @param defaultValue
	 *            the default value when key is not found or any error occurred
	 * @return the property value as long
	 */
	public Long getLongProperty(String key, Long defaultValue);

	/**
	 * Return the short property value with the given key, or {@code defaultValue}
	 * if the key doesn't exist.
	 *
	 * @param key
	 *            the property name
	 * @param defaultValue
	 *            the default value when key is not found or any error occurred
	 * @return the property value as short
	 */
	public Short getShortProperty(String key, Short defaultValue);

	/**
	 * Return the float property value with the given key, or {@code defaultValue}
	 * if the key doesn't exist.
	 *
	 * @param key
	 *            the property name
	 * @param defaultValue
	 *            the default value when key is not found or any error occurred
	 * @return the property value as float
	 */
	public Float getFloatProperty(String key, Float defaultValue);

	/**
	 * Return the double property value with the given key, or {@code defaultValue}
	 * if the key doesn't exist.
	 *
	 * @param key
	 *            the property name
	 * @param defaultValue
	 *            the default value when key is not found or any error occurred
	 * @return the property value as double
	 */
	public Double getDoubleProperty(String key, Double defaultValue);

	/**
	 * Return the byte property value with the given key, or {@code defaultValue} if
	 * the key doesn't exist.
	 *
	 * @param key
	 *            the property name
	 * @param defaultValue
	 *            the default value when key is not found or any error occurred
	 * @return the property value as byte
	 */
	public Byte getByteProperty(String key, Byte defaultValue);

	/**
	 * Return the boolean property value with the given key, or {@code defaultValue}
	 * if the key doesn't exist.
	 * 
	 * @param key
	 *            the property name
	 * @param defaultValue
	 *            the default value when key is not found or any error occurred
	 * @return the property value as boolean
	 */
	public Boolean getBooleanProperty(String key, Boolean defaultValue);
}
