
import java.io.File;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author partho
 */
public class databaseHelper {
    
    String dbPath=null;
    
    public databaseHelper(){
        
    }
    
    void saveData(String x,String y, String xAxisLabel, String yAxisLabel, String graphTitle, String lineName){
        try {
            Class.forName("org.sqlite.JDBC");
            dbPath=databaseHelper.class.getProtectionDomain().getCodeSource().getLocation().toURI().toString();
            dbPath=dbPath.substring(dbPath.indexOf("/"),dbPath.length());
            dbPath=dbPath.substring(0,dbPath.lastIndexOf("/"));
            File file=new File(dbPath+"/db");
            file.mkdir();
            try{
               Connection connection=DriverManager.getConnection("jdbc:sqlite:"+dbPath+"/db/graphData.db");
               System.out.println("db created at "+dbPath+"/db/graphData.db");
               try(Statement statement=connection.createStatement()){
                   String queryString="CREATE TABLE IF NOT EXISTS data(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                           + "x TEXT NOT NULL, y TEXT NOT NULL, xAxisLabel TEXT NOT NULL, yAxisLabel TEXT NOT NULL, "
                           + "graphTitle TEXT NOT NULL, lineName TEXT NOT NULL, dateTime TEXT NOT NULL)";
                   statement.executeUpdate(queryString);
                   System.out.println("table created");
                   
                   SimpleDateFormat formater =new SimpleDateFormat ("yyyy/MM/dd hh:mm:ss");
                   Date date=(Date) Calendar.getInstance().getTime();
                   String curTime=formater.format(date);
                   System.out.println(curTime);
                   queryString="INSERT INTO data(x, y, xAxisLabel, yAxisLabel, graphTitle, lineName, dateTime) "
                           + "VALUES ('"+x+"','"+y+"',?,?,?,?,'"+curTime+"')";
                   PreparedStatement preparedStatement=connection.prepareStatement(queryString);
                   preparedStatement.setString(1,xAxisLabel);
                   preparedStatement.setString(2,yAxisLabel);
                   preparedStatement.setString(3,graphTitle);
                   preparedStatement.setString(4,lineName);
                   preparedStatement.executeUpdate();
                   System.out.println("graph data saved");
               } catch(Exception e){
                   System.out.println(e);
               }
            }catch(Exception e){
                System.out.println(e);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    ArrayList fetchData(){
        ArrayList retVal =new ArrayList();
        ResultSet resultSet=null;
        try{
            Class.forName("org.sqlite.JDBC");
            dbPath=databaseHelper.class.getProtectionDomain().getCodeSource().getLocation().toURI().toString();
            dbPath=dbPath.substring(dbPath.indexOf("/"),dbPath.length());
            dbPath=dbPath.substring(0,dbPath.lastIndexOf("/"));
            try(Connection connection=DriverManager.getConnection("jdbc:sqlite:"+dbPath+"/db/graphData.db"); Statement statement=connection.createStatement()){
                String queryString="SELECT * FROM data";
                resultSet=statement.executeQuery(queryString);
                while(resultSet.next()){
                    retVal.add(resultSet.getString(2));
                    retVal.add(resultSet.getString(3));
                    retVal.add(resultSet.getString(4));
                    retVal.add(resultSet.getString(5));
                    retVal.add(resultSet.getString(6));
                    retVal.add(resultSet.getString(7));
                    retVal.add(resultSet.getString(8));
                }
                resultSet.close();
            }catch(Exception e){
                System.out.println(e);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        
        return retVal;
    }
    
    boolean existPoints(String x,String y){
        boolean retVal=false;
        try{
            Class.forName("org.sqlite.JDBC");
            dbPath=databaseHelper.class.getProtectionDomain().getCodeSource().getLocation().toURI().toString();
            dbPath=dbPath.substring(dbPath.indexOf("/"),dbPath.length());
            dbPath=dbPath.substring(0,dbPath.lastIndexOf("/"));
            try(Connection connection=DriverManager.getConnection("jdbc:sqlite:"+dbPath+"/db/graphData.db"); Statement statement=connection.createStatement();){
                PreparedStatement preparedStatement=connection.prepareStatement("SELECT COUNT(*) AS rows FROM data WHERE x=? AND y=?");
                preparedStatement.setString(1,x);
                preparedStatement.setString(2,y);
                ResultSet resultSet=preparedStatement.executeQuery();
                while(resultSet.next()){
                    if(Integer.parseInt(resultSet.getString("rows"))>0){
                        retVal=true;
                    }
                }
            }catch(Exception e){
                System.out.println(e);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return retVal;
    }
}
