import java.util.Scanner;

public class PisteidenLasku {

	public static void main(String[] args) {
		// Muuttujien alustus
		Scanner lukija = new Scanner(System.in);
		String pelinNimi, auttelia;
		int maxPisteet, pelM��r�, tiputaanPist = 0, ylitys, kierros = 0;
		boolean eiVoittajaa = true;
		String aloitus = "Tervetuloa pelaamaan!\n\n"
				+ "Ohjelma laskee puolestasi pelaajien pisteit� sek� ilmoittaa voittajan l�ydytty�\n"
				+ "Ohjelmaan sy�tet��n pisteet kokonaislukuina.\n" + "Pelaajia voi olla 2-10 hl�\n\n";

		// Pelin alustus ja nime�minen
		System.out.print(aloitus);
		System.out.println("Anna pelille nimi: ");
		pelinNimi = lukija.nextLine();

		// Pelaajijen m��r�n sy�tt�
		System.out.println("Monta pelaajaa osallistuu?");
		pelM��r� = lukija.nextInt();
		auttelia = lukija.nextLine();
		while (pelM��r� < 2 || pelM��r� > 10) {
			System.out.println(
					"Sy�tt�m�si luku ei kelpaa.\n" + "Pelaajia pit�� olla v�hint��n 2 ja korkeintaan voi olla 10");
			System.out.println("Monta pelaajaa osallistuu?");
			pelM��r� = lukija.nextInt();
			auttelia = lukija.nextLine();
		}

		String[] pelaajat = new String[pelM��r�];

		// Pelaajien nime�minen
		for (int i = 0; i < pelM��r�; i++) {
			System.out.println("Anna " + (i + 1) + ". pelaajan nimi: ");
			pelaajat[i] = lukija.nextLine();
		}

		// Pelattavan pistem��r�n sy�tt�
		System.out.println("Mihin pistem��r��n pelataan?");
		maxPisteet = lukija.nextInt();
		while (maxPisteet < 1) {
			System.out.println("Sy�tt�m�si luku ei kelpaa!\n" + "Sy�t� luku joka on suurempi kuin 0");
			System.out.println("Anna pistem��r� johon pelataan: ");
			maxPisteet = lukija.nextInt();
		}

		// Valinta pit��k� pelattavaan pistem��r��n p��st� tasaluvulla
		do {
			System.out.println(
					"Jos yll� olevaan pistem��r��n pit�� p��st� tasaluvulla, voit m��ritell� luvun johon tiputaan valitsemalla 1\n"
							+ "Jos voittamiseen riitt�� ett� ylitt�� yll� olevan pistem��r�n valitse 0");
			ylitys = lukija.nextInt();
		} while (ylitys != 1 && ylitys != 0);

		// Jos pit�� p��st� tasaluvulla pelattavaan pistem��r��n, kysyt��n mihin
		// pistem��r��n tiputaan jos se ylittyy
		if (ylitys == 1) {
			System.out.println("Anna pistem��r� johon tiputaan tavoiteltavan pistem��r�n ylittyess�: ");
			tiputaanPist = lukija.nextInt();
			while (tiputaanPist > maxPisteet || tiputaanPist < 0) {
				System.out.println("Sy�tt�m�si luku ei kelpaa!\n"
						+ "Sy�t� luku joka on suurempi kuin 0 ja pienempi kuin " + maxPisteet);
				System.out.println("Anna pistem��r� johon tiputaan: ");
				tiputaanPist = lukija.nextInt();
			}
		}

		// Peli alkaa
		System.out.println("\nPeli " + pelinNimi + " voi alkaa!\n");

		int[] pisteet = new int[pelM��r�];

		// Py�ritet��n peli� niin kauan kuin voittaja l�ytyy
		while (eiVoittajaa) {
			System.out.println("\nKierros: " + (kierros + 1));
			eiVoittajaa = PeliKierros(pelM��r�, kierros, maxPisteet, tiputaanPist, ylitys, pelaajat, pisteet);
			kierros++;
		}

		// Voittajan julistus
		Voittaja(pelM��r�, ylitys, maxPisteet, pisteet, pelaajat);

	}

	public static void Voittaja(int pelM��r�, int ylitys, int maxPisteet, int[] pisteet, String[] pelaajat) {
		int voittajiaYht = 0;
		String[] voittajat = new String[pelM��r�];

		// Tarkastetaan onko useampi voittaja, jos pelataan niin ett� tasaluvulla on
		// p��st�v� tavoiteltuun pistem��r��n
		if (ylitys == 1) {
			for (int i = 0; i < pelM��r�; i++) {
				if (pisteet[i] == maxPisteet) {
					voittajat[voittajiaYht] = pelaajat[i];
					voittajiaYht++;
				}
			}
		}

		// Tarkastetaan onko useampi voittaja, jos pelataan niin ett� voittamiiseen
		// riitt�� tavoitellun pistem��r�n ylitt�minen
		else {
			int j = 0;
			while (j < pelM��r�) {
				if (pisteet[j] >= maxPisteet) {
					voittajat[voittajiaYht] = pelaajat[j];
					voittajiaYht++;
				}
				j++;
			}
		}

		// Tulostetaan voittaja/voittajat
		System.out.println("\nPeli p��ttyi!\n" + "Pelin voitti: ");
		for (int k = 0; k < voittajat.length; k++)
			if (voittajat[k] != null) {
				System.out.print(voittajat[k] + "\n");
			}
	}

	public static Boolean PeliKierros(int pelM��r�, int kierros, int maxPisteet, int tiputaanPist, int ylitys,
			String[] pelaajat, int[] pisteet) {
		// Alustetaan lis��� muuttujia
		Boolean jatkuu = true;
		int pelaajaNro = 0;
		Scanner kierrosLuk = new Scanner(System.in);

		// Pisteiden sy�tt� pelaajalle
		while (pelaajaNro < pelM��r�) {
			int kierrosPist = 0;
			System.out.println("\nPelaajalla " + pelaajat[pelaajaNro] + ", on pisteit� on t�ll� hetkell� "
					+ pisteet[pelaajaNro] + ", anna pisteet kierrokselta: " + (kierros + 1));
			kierrosPist = kierrosLuk.nextInt();
			pisteet[pelaajaNro] += kierrosPist;
			System.out.println("Pisteit� yhteens�: " + pisteet[pelaajaNro]);

			// Voiton ja tavoitellun pistem��r�n ylityksen tarkastaminen, silloin kun
			// voittoon pit�� p��st� tasaluvulla
			if (ylitys == 1) {
				if (pisteet[pelaajaNro] > maxPisteet) {
					pisteet[pelaajaNro] = tiputaanPist;
					System.out
							.println("Ylitit tavoiteltavan pistem��r�n!\n" + "Tipuit " + tiputaanPist + " pisteeseen.");
				} else {
					if (pisteet[pelaajaNro] == maxPisteet) {
						jatkuu = false;
						System.out.println("\nVoitit pelin!\n" + "Pelaamme viel� kierroksen loppuun");
					}
				}
			}

			// Voiton tarkastaminen, silloin kun voittoon ei pid� p��st� tasaluvulla
			else {
				if (pisteet[pelaajaNro] >= maxPisteet) {
					jatkuu = false;
					System.out.println("\nVoitit pelin!\n" + "Pelaamme viel� kierroksen loppuun");
				}
			}
			pelaajaNro++;
		}

		// Palautetaan tarpeen mukaan true tai false riippuen onko voittaja l�ytynyt
		if (jatkuu == true) {
			return true;
		} else {
			return false;
		}

	}

}
