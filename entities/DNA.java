package entities;

import java.util.Random;

import entities.traits.Trait;

public class DNA {
	private static final int TRAIT_COUNT = Trait.Type.values().length;
	private static final int BYTES_PER_TRAIT = 4;

	private static final int SIZE = TRAIT_COUNT * BYTES_PER_TRAIT + 1;

	private String strand;

	public DNA(String s) {
		this.strand = s;

	}

	public static String generateStrand() {
		char[] text = new char[SIZE];
		Random r = new Random();

		for (int i = 0; i < SIZE; i++) {
			text[i] = (char) r.nextInt(255);
		}

		return new String(text);
	}

	public static String generateStrand(Entity.Type gender) {
		char[] text = new char[SIZE];
		Random r = new Random();

		for (int i = 0; i < SIZE; i++) {
			text[i] = (char) r.nextInt(255);
		}

		if (gender == Entity.Type.FEMALE)
			while (bitSum(text[SIZE - 1]) % 2 != 0) {
				text[SIZE - 1] = (char) r.nextInt(255);
			}
		else if (gender == Entity.Type.MALE)
			while (bitSum(text[SIZE - 1]) % 2 == 0) {
				text[SIZE - 1] = (char) r.nextInt(255);
			}

		return new String(text);
	}

	public static void printDNABits(DNA d) {
		String s = d.getStrand();

		for (int i = 0; i < SIZE; i++) {

			System.out.println(String.format("%8s", Integer.toBinaryString(s.charAt(i))).replace(' ', '0'));

			if ((i + 1) % BYTES_PER_TRAIT == 0) {
				System.out.println();
			}
		}
	}

	public int getTraitInfluence(Trait.Type t) {
		int p = t.ordinal();

		int sum = 0;
		for (int i = p; i < p + BYTES_PER_TRAIT; i++) {
			sum += bitSum(strand.charAt(i));
		}

		return (int) (((double) sum / (8 * BYTES_PER_TRAIT)) * 100);
	}

	public String mutate() {
		char[] mutated = strand.toCharArray();
		Random r = new Random();

		int targetGene = r.nextInt(TRAIT_COUNT * BYTES_PER_TRAIT);

		mutated[targetGene] = (char) r.nextInt(255);

		return new String(mutated);
	}

	public static DNA combine(DNA male, DNA female) {
		char[] result = new char[SIZE];
		Random r = new Random();

		for (int i = 0; i < SIZE; i++) {
			double chance = r.nextDouble();
			if (chance > 0.5) {
				result[i] = male.getStrand().charAt(i);
			} else {
				result[i] = female.getStrand().charAt(i);
			}
		}

		return new DNA(new String(result));
	}

	public Entity.Type getGender() {
		char genderGene = strand.charAt(SIZE - 1);

		if (genderGene % 2 == 0) {
			return Entity.Type.FEMALE;
		} else {
			return Entity.Type.MALE;
		}
	}

	public String getStrand() {
		return this.strand;
	}

	public static int bitSum(char c) {
		int sum = 0;
		for (int i = 0; i < 8; i++) {
			sum += getBit(c, i);
		}

		return sum;
	}

	public static int getBit(char c, int position) {
		return (c >> position) & 1;
	}
}
