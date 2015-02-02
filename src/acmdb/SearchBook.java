package acmdb;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.ResultSet;

public class SearchBook {
	protected static void run(String loginName) throws IOException, SQLException{
		while (true){
			System.out.println("\nChoose your way to search :");
			System.out.println("1. 'AND' of some conditions.");
			System.out.println("2. 'OR' of some conditions.");
			System.out.println("3. back.");
			Integer way = YwkDriver.readInt();
			if (way==null || way>3 || way<1) {
				System.out.println("\nIllegal input!");
				continue;
			}
			
			if (way==3) return;
			else {
				String []con = new String[5];
				con[0] = "";
				
				System.out.println("\nPlease input one of book's author's name: (enter '%' to ignore this condition)");
				String author = YwkDriver.readString(100);
				if (author==null) continue;
				if (author.equals("%")) con[1] = null;
				else con[1]=" r.authorName='"+author;
						
				System.out.println("\nPlease input publisher's name: (enter '%' to ignore this condition)");
				String publisher = YwkDriver.readString(100);
				if (publisher==null) continue;
				if (publisher.equals("%")) con[2] = null;
				else con[2]=" b.publisher='"+publisher+"' ";
				
				System.out.println("\nPlease input some word in title: (enter '%' to ignore this condition)");
				String title = YwkDriver.readString(100);
				if (title==null) continue;
				if (title.equals("%")) con[3] = null;
				else con[3]=" b.title LIKE '%"+title+"%' ";
				
				System.out.println("\nPlease input some key of subject: (enter '%' to ignore this condition)");
				String subject = YwkDriver.readString(100);
				if (subject==null) continue;
				if (subject.equals("%")) con[4] = null;
				else con[4] =" b.subject LIKE '%"+subject+"%' ";
				
				for (int i=1;i<=4;++i)
					if (con[i]!=null){
						if (con[0]=="") {
							con[0] = con[i];
							continue;
						}
						if (way==1) con[0] = con[0] + "AND" + con[i];
						else con[0] = con[0] + "OR" + con[i];
					}
				//if (con[0]!="") con[0] = " WHERE "+con[0];
				if (con[0] == "")
					con[0] = "(1=1)";
				System.out.println("\nPlease select the order of the books you search for (1:by year.  2:by average score of feedbacks.  3.by average score of trusted feedbacks)");
				Integer order = YwkDriver.readInt();
				if (order==null || order<1 || order>3) order = 1;
				
				if (order == 1)
					con[0] = "SELECT DISTINCT b.ISBN, b.title, b.yearOfPublication, b.publisher, b.numberOfCopies, b.subject " +
							"FROM Book b left join AuthorOf r on b.ISBN = r.ISBN where " +
							con[0] + " order by b.yearOfPublication;";
				else if (order == 2)
				/*	con[0] = "SELECT b.ISBN, b.title, b.yearOfPublication, b.publisher, b.subject, avg(f.score) as avgScore" +
							" FROM Book b, Author a, AuthorOf r, Feedback f where (" +
							con[0] + ") and f.feedbackTo = b.ISBN group by b.ISBN order by avgScore";*/
					con[0] = "Select DISTINCT bb.ISBN, bb.title, bb.yearOfPublication, bb.publisher, bb.numberOfCopies, bb.subject, avg(f.score) as avgScore from (SELECT b.ISBN, b.title, b.yearOfPublication, b.numberOfCopies, b.publisher, b.subject" +
							" FROM Book b left join AuthorOf r on r.ISBN = b.ISBN where" + con[0] + ") As bb left join Feedback f on f.feedbackTo = bb.ISBN group by bb.ISBN order by avgScore DESC;";
				else if (order == 3)
					con[0] = "Select DISTINCT bb.ISBN, bb.title, bb.yearOfPublication, bb.publisher, bb.numberOfCopies, bb.subject, avg(f.score) as avgTrustedScore from (SELECT b.ISBN, b.title, b.yearOfPublication, b.publisher, b.numberOfCopies, b.subject" +
							" FROM Book b left join AuthorOf r on r.ISBN = b.ISBN where" + con[0] + ") As bb left join "
									+ "(select ff.feedbackTo, ff.score from Feedback ff, Declaration d "
									+ "where ff.feedbackFrom = d.userBeingDeclared and d.declaringUser = '" + loginName + "' and d.comment = 1) as f on f.feedbackTo = bb.ISBN group by bb.ISBN order by avgTrustedScore DESC;";
				ResultSet results = YwkDriver.executeSqlQuery(con[0]);
				
				if (!results.next()) System.out.println("\nNo book match your condition!");
				else //BookBrowser.printBookBrowse(loginName, results, order);
					Lib.printResultSet(results);
			}}
		}
	
	public static String jspRun(String loginName, int style, int order, String author, String publisher, String title, String subject) throws SQLException{
			Connector con = new Connector();
			Integer way = order;
			String []cond = new String[5];
			cond[0] = "";
		
			if (author.equals("%")) cond[1] = null;
			else cond[1]=" r.authorName='"+author;
						
			if (publisher.equals("%")) cond[2] = null;
			else cond[2]=" b.publisher='"+publisher+"' ";
				
			if (title.equals("%")) cond[3] = null;
			else cond[3]=" b.title LIKE '%"+title+"%' ";
				
			if (subject.equals("%")) cond[4] = null;
			else cond[4] =" b.subject LIKE '%"+subject+"%' ";
				
				for (int i=1;i<=4;++i)
					if (cond[i]!=null){
						if (cond[0]=="") {
							cond[0] = cond[i];
							continue;
						}
						if (way==1) cond[0] = cond[0] + "AND" + cond[i];
						else cond[0] = cond[0] + "OR" + cond[i];
					}
				//if (con[0]!="") con[0] = " WHERE "+con[0];
				if (cond[0] == "")
					cond[0] = "(1=1)";
				
				if (order == 1)
					cond[0] = "SELECT DISTINCT b.ISBN, b.title, b.yearOfPublication, b.publisher, b.numberOfCopies, b.subject " +
							"FROM Book b left join AuthorOf r on b.ISBN = r.ISBN where " +
							cond[0] + " order by b.yearOfPublication;";
				else if (order == 2)
				/*	con[0] = "SELECT b.ISBN, b.title, b.yearOfPublication, b.publisher, b.subject, avg(f.score) as avgScore" +
							" FROM Book b, Author a, AuthorOf r, Feedback f where (" +
							con[0] + ") and f.feedbackTo = b.ISBN group by b.ISBN order by avgScore";*/
					cond[0] = "Select DISTINCT bb.ISBN, bb.title, bb.yearOfPublication, bb.publisher, bb.numberOfCopies, bb.subject, avg(f.score) as avgScore from (SELECT b.ISBN, b.title, b.yearOfPublication, b.numberOfCopies, b.publisher, b.subject" +
							" FROM Book b left join AuthorOf r on r.ISBN = b.ISBN where" + cond[0] + ") As bb left join Feedback f on f.feedbackTo = bb.ISBN group by bb.ISBN order by avgScore DESC;";
				else if (order == 3)
					cond[0] = "Select DISTINCT bb.ISBN, bb.title, bb.yearOfPublication, bb.publisher, bb.numberOfCopies, bb.subject, avg(f.score) as avgTrustedScore from (SELECT b.ISBN, b.title, b.yearOfPublication, b.publisher, b.numberOfCopies, b.subject" +
							" FROM Book b left join AuthorOf r on r.ISBN = b.ISBN where" + cond[0] + ") As bb left join "
									+ "(select ff.feedbackTo, ff.score from Feedback ff, Declaration d "
									+ "where ff.feedbackFrom = d.userBeingDeclared and d.declaringUser = '" + loginName + "' and d.comment = 1) as f on f.feedbackTo = bb.ISBN group by bb.ISBN order by avgTrustedScore DESC;";
				ResultSet results = YwkDriver.executeSqlQuery(con, cond[0]);
				
				String ans;
				
				if (!results.next()) ans = "No book match your condition!";
				else ans = Lib.printJSPResultSet(results);		
				con.closeConnection();
				return ans;
	}
}
