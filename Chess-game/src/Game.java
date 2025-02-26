public class Game {
    private String winnerName;
    private String loserName;
    private String firstPlayerName;
    private String secondPlayerName;
    private double firstPlayerScore;
    private double secondPlayerScore;
    private Long time;
 
     public Game(String winnerName, String loserName,double firstPlayerScore,double secondPlayerScore, String firstPlayerName,
     String secondPlayerName,Long time) {
         this.winnerName = winnerName;
         this.loserName = loserName;
         this.firstPlayerScore =firstPlayerScore;
         this.secondPlayerScore = secondPlayerScore;
         this.firstPlayerName = firstPlayerName;
         this.secondPlayerName = secondPlayerName;
         this.time = time;
     }
     public String getloserName(){
         return this.loserName;
     }
 
     public String getWinnerName(){
         return this.winnerName;
     }
 
     public String getSecondPlayerName(){
         return this.secondPlayerName;
     }
 
     public String getFirstPlayerName(){
         return this.firstPlayerName;
     } 
 
     public double getFirstPlayerScore(){
         return this.firstPlayerScore;
     }
 
     public double getSecondPlayerScore(){
         return this.secondPlayerScore;
     }
 
     public Long getTime(){
         return this.time;
     }
}
