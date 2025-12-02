package com.emlett.aoc.utils

/**
 * Modifies a key-value pair in the map by applying a transformation function to the existing value associated with the
 * key.
 *
 * @param key The key of the key-value pair to be added.
 * @param block The transformation function to be applied to the existing value associated with the key.
 * @return A new map with the added key-value pair.
 * @throws NoSuchElementException if [key] is not present in the map
 */
fun <K, V> Map<K, V>.with(key: K, block: (V) -> V) = this + (key to block(getValue(key)))

fun <K, V : Any> Map<K, V>.merge(other: Map<K, V>, mergeFunction: (V, V) -> V): Map<K, V> {
  return toMutableMap().also { map -> other.forEach { (k, v) -> map[k] = map[k]?.let { mergeFunction(it, v) } ?: v } }
}
