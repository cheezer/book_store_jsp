package acmdb;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class BookBrowser {
	
	public static void printSuggestBooks(String ISBN, ResultSet results) throws IOException, SQLException
	{
		ArrayList<Book> tmpList = new ArrayList<Book>();
		results.beforeFirst();
		while (results.next()){
			Book tmp = new Book();
			tmp.loginName = "None";
			tmp.ISBN = results.getString("ISBN");
			tmp.title = results.getString("title");
			if (tmp.title==null) tmp.title = "null";
			tmp.publisher = results.getString("publisher");
			if (tmp.publisher==null) tmp.publisher = "null";
			tmp.subject = results.getString("subject");
			if (tmp.subject==null) tmp.subject = "null";
			tmp.yearOfPublication = results.getInt("yearOfPublication");
			if (tmp.yearOfPublication==null) tmp.yearOfPublication = -1;
			tmpList.add(tmp);
		}
		for (int i=0;i<tmpList.size();++i){
			tmpList.get(i).update();
		}
		if (!tmpList.isEmpty())
			System.out.println("I suggest the following books to you:");
		outputList(tmpList);
	}
	
	protected static void printBookBrowse(String loginName, ResultSet results, int orderWay) throws SQLException{
		PriorityQueue<Book> bookList;	
		ArrayList<Book> tmpList = new ArrayList<Book>();
		if (orderWay==1)  bookList = new PriorityQueue<Book>(1,
										new Comparator<Book>(){
										 public int compare(Book b1, Book b2){
											 if (b1.yearOfPublication==-1) return 1;
											 if (b2.yearOfPublication==-1) return -1;
											 if (b1.yearOfPublication<b2.yearOfPublication) return -1;
											 else if (b1.yearOfPublication>b2.yearOfPublication) return 1;
											 else return 0;
										 }} );
		else if (orderWay==2)  bookList = new PriorityQueue<Book>(1,
				new Comparator<Book>(){
				 public int compare(Book b1, Book b2){
					 if (b1.avgFeedbackScore>b2.avgFeedbackScore) return -1;
					 else if (b1.avgFeedbackScore<b2.avgFeedbackScore) return 1;
					 else return 0;
				 }} );
		else if (orderWay == -1) bookList = new PriorityQueue<Book>(1,
				new Comparator<Book>(){
			 public int compare(Book b1, Book b2){
				 if (b1.sales>b2.sales) return -1;
				 else if (b1.sales<b2.sales) return 1;
				 else return 0;
			 }} );
		else bookList = new PriorityQueue<Book>(1,
				new Comparator<Book>(){
				 public int compare(Book b1, Book b2){
					 if (b1.trustedFeedbackScore>b2.trustedFeedbackScore) return -1;
					 else if (b1.trustedFeedbackScore<b2.trustedFeedbackScore) return 1;
					 else return 0;
				 }} );
		//Initialize the vessel!
		
		results.beforeFirst();
		while (results.next()){
			Book tmp = new Book();
			tmp.loginName = loginName;
			tmp.ISBN = results.getString("ISBN");
			tmp.title = results.getString("title");
			if (tmp.title==null) tmp.title = "null";
			tmp.publisher = results.getString("publisher");
			if (tmp.publisher==null) tmp.publisher = "null";
			tmp.subject = results.getString("subject");
			if (tmp.subject==null) tmp.subject = "null";
			tmp.yearOfPublication = results.getInt("yearOfPublication");
			if (tmp.yearOfPublication==null) tmp.yearOfPublication = -1;
			tmpList.add(tmp);
		}
		
		for (int i=0;i<tmpList.size();++i){
			tmpList.get(i).update();
			bookList.add(tmpList.get(i));
		}
		
		output(bookList);
	}
	
	private static void output(PriorityQueue<Book> bookList){
		ArrayList<Book> tmpList = new ArrayList<Book>();
		while (!bookList.isEmpty())
			tmpList.add(bookList.poll());
		outputList(tmpList);
	}

	/*private static void output(PriorityQueue<Book> bookList){
		System.out.println();
		int[] width = {4,5,9,7,9,17,16,20};
		ArrayList<Book> tmpList = new ArrayList<Book>();
		Book tmp;
		while (!bookList.isEmpty()){
			tmp = bookList.poll();
			tmpList.add(tmp);
			
			width[0] = Math.max(width[0], tmp.ISBN.length());
			width[1] = Math.max(width[1], tmp.title.length());
			width[2] = Math.max(width[2], tmp.publisher.length());
			width[3] = Math.max(width[3], tmp.subject.length());
			width[4] = Math.max(width[4], tmp.authorLen);
			width[5] = Math.max(width[5], tmp.yearOfPublication.toString().length());
			width[6] = Math.max(width[6], String.valueOf(tmp.avgFeedbackScore).length());
			width[7] = Math.max(width[7], String.valueOf(tmp.trustedFeedbackScore).length());
		}
		
		for (int i=0;i<8;++i)
			width[i]+=3;
		System.out.print("ISBN"); for (int i=0;i<width[0]-"ISBN".length();++i) System.out.print(" ");
		System.out.print("title"); for (int i=0;i<width[1]-"title".length();++i) System.out.print(" ");
		System.out.print("publisher"); for (int i=0;i<width[2]-"publisher".length();++i) System.out.print(" ");
		System.out.print("subject"); for (int i=0;i<width[3]-"subject".length();++i) System.out.print(" ");
		System.out.print("authorName"); for (int i=0;i<width[4]-"authorName".length();++i) System.out.print(" ");
		System.out.print("yearOfPublication"); for (int i=0;i<width[5]-"yearOfPublication".length();++i) System.out.print(" ");
		System.out.print("avgFeedbackScore"); for (int i=0;i<width[6]-"avgFeedbackScore".length();++i) System.out.print(" ");
		System.out.print("trustedFeedbackScore"); for (int i=0;i<width[7]-"trustedFeedbackScore".length();++i) System.out.print(" ");
		System.out.println();
		
		for (int j=0;j<tmpList.size();++j){
			tmp = tmpList.get(j);
			System.out.print(tmp.ISBN); for (int i=0;i<width[0]-tmp.ISBN.length();++i) System.out.print(" ");
			System.out.print(tmp.title); for (int i=0;i<width[1]-tmp.title.length();++i) System.out.print(" ");
			System.out.print(tmp.publisher); for (int i=0;i<width[2]-tmp.publisher.length();++i) System.out.print(" ");
			System.out.print(tmp.subject); for (int i=0;i<width[3]-tmp.subject.length();++i) System.out.print(" ");
			System.out.print(tmp.authorName); for (int i=0;i<width[4]-tmp.authorLen;++i) System.out.print(" ");
			System.out.print(tmp.yearOfPublication); for (int i=0;i<width[5]-tmp.yearOfPublication.toString().length();++i) System.out.print(" ");
			if (tmp.avgFeedbackScore>0) {System.out.print(tmp.avgFeedbackScore); for (int i=0;i<width[6]-String.valueOf(tmp.avgFeedbackScore).length();++i) System.out.print(" ");}
				else {System.out.print("None"); for (int i=0;i<width[6]-"None".length();++i) System.out.print(" ");}
			if (tmp.trustedFeedbackScore>0) {System.out.print(tmp.trustedFeedbackScore); for (int i=0;i<width[7]-String.valueOf(tmp.trustedFeedbackScore).length();++i) System.out.print(" ");}
				else {System.out.print("None"); for (int i=0;i<width[7]-"None".length();++i) System.out.print(" ");}
			System.out.println();			
		}
	}*/
	
	private static void outputList(ArrayList<Book> tmpList){
		System.out.println();
		int[] width = {4,5,9,7,9,17,16,20,5};
		Book tmp;
		for (int i = 0; i < tmpList.size(); i++)
		{
			tmp = tmpList.get(i);
			width[0] = Math.max(width[0], tmp.ISBN.length());
			width[1] = Math.max(width[1], tmp.title.length());
			width[2] = Math.max(width[2], tmp.publisher.length());
			width[3] = Math.max(width[3], tmp.subject.length());
			width[4] = Math.max(width[4], tmp.authorLen);
			width[5] = Math.max(width[5], tmp.yearOfPublication.toString().length());
			width[6] = Math.max(width[6], String.valueOf(tmp.avgFeedbackScore).length());
			width[7] = Math.max(width[7], String.valueOf(tmp.trustedFeedbackScore).length());
			width[8] = Math.max(width[8], String.valueOf(tmp.sales).length());
		}
		
		for (int i=0;i<8;++i)
			width[i]+=3;
		System.out.print("ISBN"); for (int i=0;i<width[0]-"ISBN".length();++i) System.out.print(" ");
		System.out.print("title"); for (int i=0;i<width[1]-"title".length();++i) System.out.print(" ");
		System.out.print("publisher"); for (int i=0;i<width[2]-"publisher".length();++i) System.out.print(" ");
		System.out.print("subject"); for (int i=0;i<width[3]-"subject".length();++i) System.out.print(" ");
		System.out.print("authorName"); for (int i=0;i<width[4]-"authorName".length();++i) System.out.print(" ");
		System.out.print("yearOfPublication"); for (int i=0;i<width[5]-"yearOfPublication".length();++i) System.out.print(" ");
		System.out.print("avgFeedbackScore"); for (int i=0;i<width[6]-"avgFeedbackScore".length();++i) System.out.print(" ");
		System.out.print("trustedFeedbackScore"); for (int i=0;i<width[7]-"trustedFeedbackScore".length();++i) System.out.print(" ");
		System.out.print("sales"); for (int i=0;i<width[8]-"sales".length();++i) System.out.print(" ");
		System.out.println();
		for (int j=0;j<tmpList.size();++j){
			tmp = tmpList.get(j);
			System.out.print(tmp.ISBN); for (int i=0;i<width[0]-tmp.ISBN.length();++i) System.out.print(" ");
			System.out.print(tmp.title); for (int i=0;i<width[1]-tmp.title.length();++i) System.out.print(" ");
			System.out.print(tmp.publisher); for (int i=0;i<width[2]-tmp.publisher.length();++i) System.out.print(" ");
			System.out.print(tmp.subject); for (int i=0;i<width[3]-tmp.subject.length();++i) System.out.print(" ");
			System.out.print(tmp.authorName); for (int i=0;i<width[4]-tmp.authorLen;++i) System.out.print(" ");
			System.out.print(tmp.yearOfPublication); for (int i=0;i<width[5]-tmp.yearOfPublication.toString().length();++i) System.out.print(" ");
			if (tmp.avgFeedbackScore>0) {System.out.print(tmp.avgFeedbackScore); for (int i=0;i<width[6]-String.valueOf(tmp.avgFeedbackScore).length();++i) System.out.print(" ");}
				else {System.out.print("None"); for (int i=0;i<width[6]-"None".length();++i) System.out.print(" ");}
			if (tmp.trustedFeedbackScore>0) {System.out.print(tmp.trustedFeedbackScore); for (int i=0;i<width[7]-String.valueOf(tmp.trustedFeedbackScore).length();++i) System.out.print(" ");}
				else {System.out.print("None"); for (int i=0;i<width[7]-"None".length();++i) System.out.print(" ");}
			System.out.print(tmp.sales); for (int i=0;i<width[5]-((Integer)tmp.sales).toString().length();++i) System.out.print(" ");

			System.out.println();			
		}
	}

	

}
