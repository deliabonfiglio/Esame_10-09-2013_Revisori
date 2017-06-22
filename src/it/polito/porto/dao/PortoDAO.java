package it.polito.porto.dao;

import it.polito.porto.bean.PortoArticle;
import it.polito.porto.bean.PortoCreator;
import it.polito.porto.bean.PortoCreatorIdMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PortoDAO {

	public List<PortoArticle> getAllArticle() {
		final String sql = "SELECT eprintid, year, title FROM article";

		List<PortoArticle> result = new ArrayList<PortoArticle>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				PortoArticle art = new PortoArticle(
						rs.getLong("eprintid"),
						rs.getInt("year"),
						rs.getString("title")
						) ;
				result.add(art);
			}

			rs.close();
			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public List<PortoArticle> getArticlesOfCreator(PortoCreator cre) {
		final String sql = "SELECT article.eprintid, year, title FROM article, authorship " +
				"WHERE article.eprintid = authorship.eprintid " +
				"AND authorship.id_creator = ?";

		List<PortoArticle> result = new ArrayList<PortoArticle>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, cre.getId()) ;

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				PortoArticle art = new PortoArticle(
						rs.getLong("eprintid"),
						rs.getInt("year"),
						rs.getString("title")
						) ;
				result.add(art);
			}

			rs.close();
			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	
	public List<PortoCreator> getAllCreator(PortoCreatorIdMap map) {
		final String sql = "SELECT id_creator, family_name, given_name FROM creator";

		List<PortoCreator> result = new ArrayList<PortoCreator>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				PortoCreator cre = map.get(rs.getInt("id_creator"));
				
				if(cre ==null){
				 cre = new PortoCreator(rs.getInt("id_creator"),
						rs.getString("given_name"),
						rs.getString("family_name")
						) ;
				 cre= map.put(cre);
				}
				
				result.add(cre);
			}

			rs.close();
			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public Set<PortoCreator> getCreatorsOfArticle(PortoArticle art, PortoCreatorIdMap map) {
		final String sql = "SELECT creator.id_creator, family_name, given_name FROM creator, authorship " +
				"WHERE authorship.id_creator=creator.id_creator " +
				"AND authorship.eprintid = ?";

		Set<PortoCreator> result = new HashSet<PortoCreator>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setLong(1, art.getEprintid()) ;

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				
				PortoCreator cre = map.get(rs.getInt("id_creator"));
				
				if(cre==null){
					cre = new PortoCreator(
						rs.getInt("id_creator"),
						rs.getString("given_name"),
						rs.getString("family_name")
						) ;
					
					cre = map.put(cre);
				}
				result.add(cre);
			}

			rs.close();
			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}


	
	public int createArticle(PortoArticle art) {
		final String sql = "INSERT INTO article (eprintid, year, title) VALUES (?, ?, ?)";

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setLong(1, art.getEprintid());
			st.setInt(2, art.getDate());
			st.setString(3, art.getTitle());

			int res = st.executeUpdate();

			st.close();
			conn.close();
			return res;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int createArticles(Collection<PortoArticle> articles) {
		final String sql = "INSERT INTO article (eprintid, year, title) VALUES (?, ?, ?)";

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			int res = 0;
			
			for (PortoArticle art : articles) {

				st.setLong(1, art.getEprintid());
				st.setInt(2, art.getDate());
				st.setString(3, art.getTitle());

				res += st.executeUpdate();
			}

			st.close();
			conn.close();
			return res;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e) ;
		}
	}

	public int createCreator(PortoCreator cre) {
		final String sql = "INSERT INTO creator (id_creator, family_name, given_name) VALUES (?, ?, ?)";

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setInt(1, cre.getId());
			st.setString(2, cre.getFamily());
			st.setString(3, cre.getGiven());

			int res = st.executeUpdate();

			st.close();
			conn.close();
			return res;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void addAuthorship(PortoCreator cre, PortoArticle art) {
		final String sql = "INSERT INTO authorship (eprintid, id_creator) VALUES (?, ?)";

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setLong(1, art.getEprintid()) ;
			st.setInt(2, cre.getId());

			st.executeUpdate();

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e) ;
		}
		
	}
	
	public List<PortoCreator> getCoauthors(PortoCreator autore, PortoCreatorIdMap authorMap) {

		final String sql = "SELECT DISTINCT c2.id_creator "+
							"FROM authorship as c1, authorship as c2 "+
							"WHERE c1.eprintid=c2.eprintid and c1.id_creator=? and c2.id_creator<> c1.id_creator "; 
		
		List<PortoCreator> coauthors = new ArrayList<PortoCreator>();
		
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, autore.getId());

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				int id= rs.getInt("id_creator");
				PortoCreator author = authorMap.get(id);
						
				coauthors.add(author);
			}
			conn.close();
			return coauthors;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}


}
