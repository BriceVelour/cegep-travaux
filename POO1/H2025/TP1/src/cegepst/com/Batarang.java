package cegepst.com;

import java.util.Random;

public class Batarang extends Power {
    public Batarang(Player player) {
        super(player);
    }

    @Override
    public void activate(Grid grid) {
        Random random = new Random();
        System.out.println("Batman utilise BATARANG pour relocaliser un symbole de Superman !");
        int oldRow = 0, oldCol = 0;
        boolean found = false;

        // Trouver un symbole de Superman à relocaliser
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (grid.getSymbol(i, j) == 'S') {
                    oldRow = i;
                    oldCol = j;
                    found = true;
                    break;
                }
            }
            if (found) break;
        }

        if (found) {
            int newRow, newCol;
            do {
                newRow = random.nextInt(4);
                newCol = random.nextInt(4);
            } while (!grid.updateCell(newRow, newCol, 'S'));

            grid.relocateSymbol(oldRow, oldCol, newRow, newCol, 'S');
        } else {
            System.out.println("Aucun symbole de Superman à relocaliser.");
        }
    }
}
