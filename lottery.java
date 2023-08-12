import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;

class Lottery {
    ToyList currentToys;
    ParticipantQueue currentParticipants;
    double lossWeight = 0;
    int lossId;

    ChanceCalc cc = new ChanceCalc();

    public Lottery(ParticipantQueue kids, ToyList tl) {

        this.currentToys = cc.assignChance(tl);
        this.currentParticipants = kids;
    }

    public void runLottery() {
        ParticipantQueue kids = this.currentParticipants;
        ToyList tl = this.currentToys;
        PriorityQueue<Toy> prizes = new PriorityQueue<>(tl.toys.values());
        try {
            BufferedWriter log = File.lotteryLog();

            while (kids.iterator().hasNext()) {
                double winRoll = cc.doRoll();
                Participant k = kids.iterator().next();
                try {
                    Toy win = cc.checkPrize(prizes, winRoll);
                    log.write(showWin(k, win) + "\n");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            log.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    String showWin(Participant kid, Toy prize) {
        String winLine;
        winLine = kid.toString() + " ваш приз " + prize.name;
        System.out.println(winLine);
        return winLine;
    }

}

class ChanceCalc {
    Random r = new Random();
    double maxChance;
    double totalWeight;

    double doRoll() {
        return r.nextDouble() * maxChance;
    }

    Toy checkPrize(PriorityQueue<Toy> prizes, double roll) throws Exception {
        PriorityQueue<Toy> onePoll = new PriorityQueue<>(prizes);
        while (!onePoll.isEmpty()) {
            Toy p = onePoll.poll();
            if (roll <= p.getChance()) {
                return checkTies(onePoll, p);
            }
        }
        throw new Exception();
    }

    ToyList assignChance(ToyList tl) {
        this.totalWeight = 0;
        this.maxChance = 0;
        for (Toy t : tl.toys.values()) {
            this.totalWeight += t.chanceWeight;
        }

        for (Toy t : tl.toys.values()) {
            double ch = t.chanceWeight / totalWeight;
            t.setChance(ch);
            if (maxChance < ch) {
                maxChance = ch;
            }
        }
        return tl;
    }

    Toy checkTies(PriorityQueue<Toy> leftovers, Toy drawn) {
        PriorityQueue<Toy> tiePoll = new PriorityQueue<>(leftovers);
        ArrayList<Toy> sameChance = new ArrayList<>();
        while (!tiePoll.isEmpty()) {
            if (drawn.getChance() == tiePoll.peek().getChance()) {
                sameChance.add(tiePoll.poll());
            } else {
                break;
            }
        }
        sameChance.add(drawn);
        int pickRandom = r.nextInt(sameChance.size());
        return sameChance.get(pickRandom);
    }

}