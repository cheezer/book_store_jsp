package acmdb;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Book {
		protected String loginName;
		protected String ISBN;
		protected String title;
		protected String publisher;
		protected String subject;
		protected ArrayList<String> authorName;
		protected Integer yearOfPublication;
		protected double avgFeedbackScore;
		protected double trustedFeedbackScore;
		protected int sales = 0;
		protected int authorLen = 0;
		
		Book(){
			authorName = new ArrayList<String>();
			avgFeedbackScore = -1;
			trustedFeedbackScore = -1;
		};
		
		protected void updateAuthor() throws SQLException{
			ResultSet results = null;
			results = YwkDriver.executeSqlQuery("SELECT a.authorName FROM AuthorOf a WHERE a.ISBN='"+this.ISBN+"'");
			while (results.next()) authorName.add(results.getString(1));
			authorLen = 0;
			for (int i=0;i<authorName.size();++i)
				authorLen += (authorName.get(i).length());
			authorLen += (authorName.size())*2;
			if (authorLen==0) authorLen = 2;
		}
		
		protected void updateAvgScore() throws SQLException {
			ResultSet results = null;
			results = YwkDriver.executeSqlQuery("SELECT AVG(f.score) FROM Feedback f WHERE f.feedbackTo='"+this.ISBN+"'");
			results.first();
			String s = results.getString(1);
			if (s==null) this.avgFeedbackScore = -1;
			else this.avgFeedbackScore = results.getDouble(1);
		}
		
		protected void updateTrustedScore() throws SQLException {
			ResultSet results = null;
			results = YwkDriver.executeSqlQuery("SELECT AVG(f.score) FROM Feedback f, Declaration d " +
												"WHERE f.feedbackTo='"+this.ISBN+"' AND d.comment=1 AND " +
												"d.declaringUser='"+this.loginName+"' AND d.userBeingDeclared=f.feedbackFrom");
			results.first();
			String s = results.getString(1);
			if (s==null) this.trustedFeedbackScore = -1;
			else this.trustedFeedbackScore = results.getDouble(1);		
		}
		
		protected void updateSales() throws SQLException
		{
			ResultSet results = null;
			results = YwkDriver.executeSqlQuery("Select sum(times) From Ordering O where O.ISBN = '" + this.ISBN + "'");
			results.first();
			this.sales = results.getInt(1);
		}
		
		protected void update() throws SQLException{
			updateAuthor();
			updateAvgScore();
			updateTrustedScore();
			updateSales();
		}
}
