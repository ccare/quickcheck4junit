package org.nebularis.test.gen;

import java.util.Random;

import org.nebularis.test.org.jcheck.generator.Gen;

public class OnesGen implements Gen<Integer> {
	private static final Integer i = 1;

	public Integer arbitrary(Random random, long size) {
		return i;
	}
}
