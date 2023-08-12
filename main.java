import java.util.List;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;


class Main {
    public static void main(String[] args) {
        ToyList toys1 = new ToyList();
        ParticipantQueue pq = new ParticipantQueue(List.of(
                new Participant("Женя"),
                new Participant("Петя"),
                new Participant("Света"),
                new Participant("Таня"),
                new Participant("Женя"),
                new Participant("Вася"),
                new Participant("Маша"),
                new Participant("Денис"),
                new Participant("Катя"),
                new Participant("Оля")));
        Lottery lot = new Lottery(pq, toys1);
        System.out.println(lot.currentToys.toString());
        lot.runLottery();
    }

}

class File {

    List<String> readToys(String filepath) {
        try {
            return Files.readAllLines(Paths.get(filepath));

        } catch(IOException e) {
            e.printStackTrace();
        }
        return List.of();
    }

     public static BufferedWriter lotteryLog() throws IOException{

             BufferedWriter bw = new BufferedWriter(
                     new OutputStreamWriter(new FileOutputStream("./winners.txt",true), StandardCharsets.UTF_8)
             );
             bw.write(LocalDateTime.now().toString()+"\n");
        return bw;
     }
}