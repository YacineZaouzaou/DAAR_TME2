import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Matcher {

	/**
	 * comparer notre egrep à celui de linux si egrep va 5 à 10 fois plus vite c'est
	 * bon ( le prof s'attend à un facteur entre 100 et 1000 )
	 */

	public static int match(char[] facteur, int[] retenue, char[] texte) {

		int i = 0;
		int j = 0;
		while (i < texte.length) {
			if (j == facteur.length)
				return (i - facteur.length);
			if (facteur[j] == texte[i]) {
				i++;
				j++;
			} else {
				if (retenue[j] == -1) {
					i++;
					j = 0;
				} else {
					j = retenue[j];
				}
			}
		}
		if (j == facteur.length) {
			return i - j;
		} else {
			return -1;
		}
	}

	public static void readFile() throws IOException {
		Path path = Paths.get("test.txt");
		long startTime = System.currentTimeMillis();
		BufferedReader reader = Files.newBufferedReader(path);
		String line = "";
		char[] patt = { 'S', 'a', 'r', 'g', 'o', 'n' };
		int idLine = 0;

		while ((line = reader.readLine()) != null) {
			if (Matcher.match(patt, chercherRetenu(patt), line.toCharArray()) != -1) {
				System.out.println(line);
				idLine++;
			}
		}
		long endTime = System.currentTimeMillis();

		System.out.println("Nombre de lignes matchées : " + idLine);
		System.out.println("Time : " + (endTime - startTime));

	}

	public static int[] chercherRetenu(char[] patt) {
		int[] retenu = new int[patt.length + 1];
		retenu[0] = -1;
		int i = 0;
		for (int j = 1; j < patt.length; j++) {
			while (i > 0 && patt[i] != patt[j])
				i = retenu[i];

			if (patt[i] == patt[j])
				++i;

			if (j < patt.length - 1 && patt[j + 1] == patt[i]) {
				retenu[j + 1] = 0;
			} else {
				retenu[j + 1] = i;
			}
			if (patt[j] == patt[0]) {
				retenu[j] = -1;
			}
		}
		return retenu;
	}

	public static void main(String[] args) throws IOException {
		readFile();
	}

}
