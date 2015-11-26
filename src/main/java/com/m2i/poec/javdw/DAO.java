package com.m2i.poec.javdw;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {

	List<T> findAll() throws SQLException;

	T find(Integer id) throws SQLException;

	void delete(T t) throws SQLException;

	T insert(T t) throws SQLException;

	void update(T t) throws SQLException;

}