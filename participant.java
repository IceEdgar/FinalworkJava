import java.util.Collection;
import java.util.Iterator;
import java.util.PriorityQueue;

class Participant implements Comparable<Participant> {
    String name;
    int id;
    static int totalCount = 0;

    public Participant(String name) {
        this.name = name;
        this.id = totalCount++;
    }

    public String toString() {
        return String.format("Участник %s, номер %d,", this.name, this.id);
    }

    public int compareTo(Participant o) {
        if (o.id == this.id) {
            return 0;
        }
        return this.id < o.id ? -1 : 1;
    }
}

class ParticipantQueue implements Iterable<Participant> {
    PriorityQueue<Participant> drawQueue;

    public ParticipantQueue(Collection<Participant> list) {
        this.drawQueue = new PriorityQueue<>(list.size());
        this.drawQueue.addAll(list);
    }

    public ParticipantQueue() {
        this.drawQueue = new PriorityQueue<>();
    }

    void addParticipant(Participant p) {
        this.drawQueue.add(p);
    }

    class ParticipantIterator implements Iterator<Participant> {
        Participant current;

        public ParticipantIterator(PriorityQueue<Participant> participants) {
            this.current = participants.peek();
        }

        public boolean hasNext() {
            return !drawQueue.isEmpty();
        }

        public Participant next() {
            return drawQueue.poll();
        }
    }

    public Iterator<Participant> iterator() {
        return new ParticipantIterator(drawQueue);
    }

}