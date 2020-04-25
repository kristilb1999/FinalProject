package TowerDefenseGame;

//imports
import java.io.InputStream;
import java.sql.*;
import java.util.Scanner;
import java.util.Vector;

/**
 * Write a description of class DatabaseDriver here.
 *
 * @author Kristi Boardman, Cameron Costello, Will Skelly, Jake Burch
 * @version Spring 2020
 */
public class DatabaseDriver {

    private static final String DATABASENAME = "./Player-Friends";

    //based on
    //https://www.novixys.com/blog/read-file-resources-folder-java/
    private static InputStream buildStream = DatabaseDriver.class.getResourceAsStream("/dropCreateSampleDatabase.txt");

    public static void setBuildStream(InputStream inputStream) {
        buildStream = inputStream;
    }

    public static int checkDatabase(InputStream inputStream) {
        setBuildStream(buildStream);
        return checkDatabase();
    }

    public static int checkDatabase() {
        int exitCode = 0;
        String queryPlayer = "SELECT * FROM Player";
        String queryFriendship = "SELECT * FROM Friendship";
        try (Connection conn = DriverManager.getConnection("jdbc:h2:" + DATABASENAME, "sa", "");) {
            PreparedStatement stmt = conn.prepareStatement(queryPlayer);
            stmt.executeQuery();
            stmt = conn.prepareStatement(queryFriendship);
            stmt.executeQuery();
        } catch (SQLException e) {
            System.err.println(e + "\n" + "Intact database not found, reloading database from build stream: " + buildStream.toString());
            exitCode = 1 + DatabaseDriver.fetchDatabase(buildStream);
        }

        return exitCode;
    }

    //resets the database to the build file
    public static int resetDatabase() {
        String queryFriendship = "DROP TABLE IF EXISTS Friendship";
        String queryPlayer = "DROP TABLE IF EXISTS Player";
        try (Connection conn = DriverManager.getConnection("jdbc:h2:" + DATABASENAME, "sa", "");) {
            PreparedStatement stmt = conn.prepareStatement(queryFriendship);
            stmt.executeUpdate();
            stmt = conn.prepareStatement(queryPlayer);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error dropping database: " + e);
            e.printStackTrace();
        }
        return DatabaseDriver.fetchDatabase(buildStream);
    }

    public static int fetchDatabase(InputStream inputStream) {
        int exitCode = 0;

            try (Connection conn = DriverManager.getConnection("jdbc:h2:" + DATABASENAME, "sa", "");) {

                Scanner sc = new Scanner(inputStream);

                String query;
                PreparedStatement stmt;
                while (sc.hasNextLine()) {

                    query = sc.nextLine();
                    stmt = conn.prepareStatement(query);
                    stmt.execute();
                }

            } catch (SQLException e) {
                System.err.println("SQL Error: " + e);
                e.printStackTrace();
                exitCode = 1;
            }

        return exitCode;
    }

    public static int addPlayer(String playerName) {
        if (playerName == null || "".equals(playerName)) {
            return 3;
        }
        return insert("INSERT INTO Player values ('" + playerName + "',0)");
    }

    public static int addFriendship(String name1, String name2) {
        if (name1 == null || "".equals(name1) || name2 == null || "".equals(name2)) {
            return 3;
        }
        return insert("INSERT INTO Friendship values ('" + name1 + "','" + name2 + "')");
    }

    private static int insert(String queryIn) {
        int exitCode = 0;

        try (Connection conn = DriverManager.getConnection("jdbc:h2:" + DATABASENAME, "sa", "");) {
            PreparedStatement stmt = conn.prepareStatement(queryIn);
            stmt.execute();
        } catch (SQLIntegrityConstraintViolationException e) {
            exitCode = 1;
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e);
            e.printStackTrace();
            exitCode = 2;
        }

        return exitCode;
    }

    public static boolean setScore(String playerName, int score) {
        boolean successful = true;
        if (playerName == null || "".equals(playerName)) {
            successful = false;
        }

        if (successful) {
            String query = "UPDATE Player SET Player.Score = " + score + " WHERE Player.Name = '" + playerName + "'";
            try (Connection conn = DriverManager.getConnection("jdbc:h2:" + DATABASENAME, "sa", "");) {
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.executeUpdate();
            } catch (SQLException e) {
                System.err.println("SQL Error: " + e);
                e.printStackTrace();
                successful = false;
            }
        }

        return successful;
    }

    public static int getScore(String playerName) {
        int score = 0;

        if (playerName != null && !"".equals(playerName)) {
            String query = "SELECT Score FROM Player WHERE Player.Name = '" + playerName + "'";
            try (Connection conn = DriverManager.getConnection("jdbc:h2:" + DATABASENAME, "sa", "");) {
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rset = stmt.executeQuery();

                rset.next();
                score = rset.getInt("Score");
            } catch (SQLException e) {
                System.err.println("SQL Error: " + e);
                e.printStackTrace();
                score = -1;
            }
        }

        return score;
    }

    public static Vector<String> getFriends(String playerName) {
        Vector friends = new Vector<String>();

        if (playerName != null && !"".equals(playerName)) {
            String query = "SELECT Name2 FROM Friendship WHERE Friendship.Name1 = '" + playerName + "'";
            try (Connection conn = DriverManager.getConnection("jdbc:h2:" + DATABASENAME, "sa", "");) {
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rset = stmt.executeQuery();

                while (rset.next()) {

                    friends.add(rset.getString("Name2"));
                }
            } catch (SQLException e) {
                System.err.println("SQL Error: " + e);
                e.printStackTrace();
            }
        }

        return friends;
    }

    public static Vector<String> getAllPlayers() {
        Vector players = new Vector<String>();
        String query = "SELECT Name FROM Player";
        try (Connection conn = DriverManager.getConnection("jdbc:h2:" + DATABASENAME, "sa", "");) {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rset = stmt.executeQuery();

            while (rset.next()) {

                players.add(rset.getString("Name"));
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e);
            e.printStackTrace();
        }

        return players;
    }
}
