package org.nebularis.test.org.jcheck;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.nebularis.test.org.jcheck.generator.Gen;
import org.nebularis.test.org.jcheck.generator.primitive.BigDecimalGen;
import org.nebularis.test.org.jcheck.generator.primitive.BigIntegerGen;
import org.nebularis.test.org.jcheck.generator.primitive.BooleanGen;
import org.nebularis.test.org.jcheck.generator.primitive.ByteGen;
import org.nebularis.test.org.jcheck.generator.primitive.CharacterGen;
import org.nebularis.test.org.jcheck.generator.primitive.DoubleGen;
import org.nebularis.test.org.jcheck.generator.primitive.FloatGen;
import org.nebularis.test.org.jcheck.generator.primitive.IntegerGen;
import org.nebularis.test.org.jcheck.generator.primitive.LongGen;
import org.nebularis.test.org.jcheck.generator.primitive.ShortGen;
import org.nebularis.test.org.jcheck.generator.primitive.StringGen;

/**
 * Utility class that helps in creating new generators.
 * 
 * @author Hampus
 */
public class Arbitrary {
    private static final Map<Class<?>, Class<? extends Gen<?>>> STD_GENERATORS = new HashMap<Class<?>, Class<? extends Gen<?>>>()
    {
    {
			put(boolean.class, BooleanGen.class);
			put(Boolean.class, BooleanGen.class);
			put(char.class, CharacterGen.class);
			put(Character.class, CharacterGen.class);
			put(byte.class, ByteGen.class);
			put(Byte.class, ByteGen.class);
			put(short.class, ShortGen.class);
			put(Short.class, ShortGen.class);
			put(int.class, IntegerGen.class);
			put(Integer.class, IntegerGen.class);
			put(long.class, LongGen.class);
			put(Long.class, LongGen.class);
			put(float.class, FloatGen.class);
			put(Float.class, FloatGen.class);
			put(double.class, DoubleGen.class);
			put(Double.class, DoubleGen.class);
			put(String.class, StringGen.class);
			put(BigInteger.class, BigIntegerGen.class);
			put(BigDecimal.class, BigDecimalGen.class);
		}
	};

	/**
	 * Get the standard (jcheck-supplied) generator for the given Java-class.
	 * 
	 * @param forClass
	 *            the type of object the generator should be for
	 * @return the standard generator for the given class (or null if no such
	 *         generator exist)
	 */
	public static <T> Gen<T> getStandardGenerator(Class<T> forClass) {
		Class<? extends Gen<?>> genClass = STD_GENERATORS.get(forClass);
		if (genClass == null) {
			return null;
		}

		Gen<?> generator;
		try {
			generator = genClass.newInstance();
		} catch (InstantiationException ex) {
			// This should not happen
			throw new RuntimeException(String.format(
					"Unable to instantiate standard generator %s.",
					genClass.getName()), ex);
		} catch (IllegalAccessException ex) {
			// This should not happen
			throw new RuntimeException(String.format(
					"Unable to instantiate standard generator %s.",
					genClass.getName()), ex);
		}

		@SuppressWarnings("unchecked")
		Gen<T> castedGenerator = (Gen<T>) generator;

		return castedGenerator;
	}

	/**
	 * Generate a random long in the interval [from-to].
	 * 
	 */
	public static long random(Random random, long from, long to) {
		return ((long) (random.nextDouble() * (to - from + 1))) + from;
	}
}
