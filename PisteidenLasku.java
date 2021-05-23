import java.util.Scanner;

public class PisteidenLasku {

	public static void main(String[] args) {
		// Muuttujien alustus
		Scanner lukija = new Scanner(System.in);
		String pelinNimi, auttelia;
		int maxPisteet, pelMäärä, tiputaanPist = 0, ylitys, kierros = 0;
		boolean eiVoittajaa = true;
		String aloitus = "Tervetuloa pelaamaan!\n\n"
				+ "Ohjelma laskee puolestasi pelaajien pisteitä sekä ilmoittaa voittajan löydyttyä\n"
				+ "Ohjelmaan syötetään pisteet kokonaislukuina.\n" + "Pelaajia voi olla 2-10 hlö\n\n";

		// Pelin alustus ja nimeäminen
		System.out.print(aloitus);
		System.out.println("Anna pelille nimi: ");
		pelinNimi = lukija.nextLine();

		// Pelaajijen määrän syöttö
		System.out.println("Monta pelaajaa osallistuu?");
		pelMäärä = lukija.nextInt();
		auttelia = lukija.nextLine();
		while (pelMäärä < 2 || pelMäärä > 10) {
			System.out.println(
					"Syöttämäsi luku ei kelpaa.\n" + "Pelaajia pitää olla vähintään 2 ja korkeintaan voi olla 10");
			System.out.println("Monta pelaajaa osallistuu?");
			pelMäärä = lukija.nextInt();
			auttelia = lukija.nextLine();
		}

		String[] pelaajat = new String[pelMäärä];

		// Pelaajien nimeäminen
		for (int i = 0; i < pelMäärä; i++) {
			System.out.println("Anna " + (i + 1) + ". pelaajan nimi: ");
			pelaajat[i] = lukija.nextLine();
		}

		// Pelattavan pistemäärän syöttö
		System.out.println("Mihin pistemäärään pelataan?");
		maxPisteet = lukija.nextInt();
		while (maxPisteet < 1) {
			System.out.println("Syöttämäsi luku ei kelpaa!\n" + "Syötä luku joka on suurempi kuin 0");
			System.out.println("Anna pistemäärä johon pelataan: ");
			maxPisteet = lukija.nextInt();
		}

		// Valinta pitääkö pelattavaan pistemäärään päästä tasaluvulla
		do {
			System.out.println(
					"Jos yllä olevaan pistemäärään pitää päästä tasaluvulla, voit määritellä luvun johon tiputaan valitsemalla 1\n"
							+ "Jos voittamiseen riittää että ylittää yllä olevan pistemäärän valitse 0");
			ylitys = lukija.nextInt();
		} while (ylitys != 1 && ylitys != 0);

		// Jos pitää päästä tasaluvulla pelattavaan pistemäärään, kysytään mihin
		// pistemäärään tiputaan jos se ylittyy
		if (ylitys == 1) {
			System.out.println("Anna pistemäärä johon tiputaan tavoiteltavan pistemäärän ylittyessä: ");
			tiputaanPist = lukija.nextInt();
			while (tiputaanPist > maxPisteet || tiputaanPist < 0) {
				System.out.println("Syöttämäsi luku ei kelpaa!\n"
						+ "Syötä luku joka on suurempi kuin 0 ja pienempi kuin " + maxPisteet);
				System.out.println("Anna pistemäärä johon tiputaan: ");
				tiputaanPist = lukija.nextInt();
			}
		}

		// Peli alkaa
		System.out.println("\nPeli " + pelinNimi + " voi alkaa!\n");

		int[] pisteet = new int[pelMäärä];

		// Pyöritetään peliä niin kauan kuin voittaja löytyy
		while (eiVoittajaa) {
			System.out.println("\nKierros: " + (kierros + 1));
			eiVoittajaa = PeliKierros(pelMäärä, kierros, maxPisteet, tiputaanPist, ylitys, pelaajat, pisteet);
			kierros++;
		}

		// Voittajan julistus
		Voittaja(pelMäärä, ylitys, maxPisteet, pisteet, pelaajat);

	}

	public static void Voittaja(int pelMäärä, int ylitys, int maxPisteet, int[] pisteet, String[] pelaajat) {
		int voittajiaYht = 0;
		String[] voittajat = new String[pelMäärä];

		// Tarkastetaan onko useampi voittaja, jos pelataan niin että tasaluvulla on
		// päästävä tavoiteltuun pistemäärään
		if (ylitys == 1) {
			for (int i = 0; i < pelMäärä; i++) {
				if (pisteet[i] == maxPisteet) {
					voittajat[voittajiaYht] = pelaajat[i];
					voittajiaYht++;
				}
			}
		}

		// Tarkastetaan onko useampi voittaja, jos pelataan niin että voittamiiseen
		// riittää tavoitellun pistemäärän ylittäminen
		else {
			int j = 0;
			while (j < pelMäärä) {
				if (pisteet[j] >= maxPisteet) {
					voittajat[voittajiaYht] = pelaajat[j];
					voittajiaYht++;
				}
				j++;
			}
		}

		// Tulostetaan voittaja/voittajat
		System.out.println("\nPeli päättyi!\n" + "Pelin voitti: ");
		for (int k = 0; k < voittajat.length; k++)
			if (voittajat[k] != null) {
				System.out.print(voittajat[k] + "\n");
			}
	}

	public static Boolean PeliKierros(int pelMäärä, int kierros, int maxPisteet, int tiputaanPist, int ylitys,
			String[] pelaajat, int[] pisteet) {
		// Alustetaan lisäää muuttujia
		Boolean jatkuu = true;
		int pelaajaNro = 0;
		Scanner kierrosLuk = new Scanner(System.in);

		// Pisteiden syöttö pelaajalle
		while (pelaajaNro < pelMäärä) {
			int kierrosPist = 0;
			System.out.println("\nPelaajalla " + pelaajat[pelaajaNro] + ", on pisteitä on tällä hetkellä "
					+ pisteet[pelaajaNro] + ", anna pisteet kierrokselta: " + (kierros + 1));
			kierrosPist = kierrosLuk.nextInt();
			pisteet[pelaajaNro] += kierrosPist;
			System.out.println("Pisteitä yhteensä: " + pisteet[pelaajaNro]);

			// Voiton ja tavoitellun pistemäärän ylityksen tarkastaminen, silloin kun
			// voittoon pitää päästä tasaluvulla
			if (ylitys == 1) {
				if (pisteet[pelaajaNro] > maxPisteet) {
					pisteet[pelaajaNro] = tiputaanPist;
					System.out
							.println("Ylitit tavoiteltavan pistemäärän!\n" + "Tipuit " + tiputaanPist + " pisteeseen.");
				} else {
					if (pisteet[pelaajaNro] == maxPisteet) {
						jatkuu = false;
						System.out.println("\nVoitit pelin!\n" + "Pelaamme vielä kierroksen loppuun");
					}
				}
			}

			// Voiton tarkastaminen, silloin kun voittoon ei pidä päästä tasaluvulla
			else {
				if (pisteet[pelaajaNro] >= maxPisteet) {
					jatkuu = false;
					System.out.println("\nVoitit pelin!\n" + "Pelaamme vielä kierroksen loppuun");
				}
			}
			pelaajaNro++;
		}

		// Palautetaan tarpeen mukaan true tai false riippuen onko voittaja löytynyt
		if (jatkuu == true) {
			return true;
		} else {
			return false;
		}

	}

}
