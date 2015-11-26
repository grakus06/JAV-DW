package com.m2i.poec.javdw;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 
 * @see <a href="http://stackoverflow.com/questions/14397134/dao-and-connections">http://stackoverflow.com/questions/14397134/dao-and-connections</a>
 * @see <a href="http://stackoverflow.com/questions/12812256/how-do-i-implement-a-dao-manager-using-jdbc-and-connection-pools">http://stackoverflow.com/questions/12812256/how-do-i-implement-a-dao-manager-using-jdbc-and-connection-pools</a>
 * 
 * @author Thomas Gros
 */
public class ArticleDAO implements DAO<Article> {
	
	private Connection conn;
	private static final String FIND_ALL_ARTICLES = "SELECT * FROM blog.article";
	private static final String FIND = "SELECT * FROM blog.article WHERE id = ?";
	private static final String INSERT = "INSERT INTO blog.article (title, content,authorId) VALUES (?,?,?)";
			  											
	public ArticleDAO(Connection conn) {
		Objects.requireNonNull(conn);
		
		this.conn = conn;
	}

	@Override
	public List<Article> findAll() throws SQLException {
		List<Article> articles = new ArrayList<>();

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(FIND_ALL_ARTICLES);

		while (rs.next()) {
			Article a = mapRsToArticle(rs);
			articles.add(a);
		}

		return articles;
	}

	@Override
	public Article find(Integer id) throws SQLException {
		Objects.requireNonNull(id);
		
		PreparedStatement stmt = conn.prepareStatement(FIND);
		stmt.setInt(1, id);
		
		ResultSet rs = stmt.executeQuery();
		rs.next();
		
		return mapRsToArticle(rs);

	}

	@Override
	public void delete(Article article) throws SQLException {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public Article insert(Article article) throws SQLException {
		Objects.requireNonNull(article);
		
		PreparedStatement stmt = conn.prepareStatement(INSERT);
		stmt.setString(1, article.getTitle());
		stmt.setString(2, article.getContent());
		stmt.setInt(3, article.getAuthorId());
		
		ResultSet rs = stmt.executeQuery();
		rs.next();
		
		return mapRsToArticle(rs);
		
	}

	@Override
	public void update(Article article) throws SQLException {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	private Article mapRsToArticle(ResultSet rs) throws SQLException {
		Objects.requireNonNull(rs);

		Article a = new Article();
		a.setId(rs.getInt("id"));
		a.setTitle(rs.getString("title"));
		a.setContent(rs.getString("content"));
		a.setAuthorId(rs.getInt("author_id"));
		a.setCreatedOn(rs.getDate("created_on"));
		return a;
	}

}
