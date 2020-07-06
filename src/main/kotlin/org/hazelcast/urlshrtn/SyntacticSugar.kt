package org.hazelcast.urlshrtn

import com.hazelcast.core.EntryEvent
import com.hazelcast.map.IMap
import com.hazelcast.map.listener.EntryAddedListener
import java.util.*

fun <K, V> IMap<K, V>.addEntryAddedListener(includeValue: Boolean = true,
                                            listener: (EntryEvent<K, V>) -> Unit): UUID =
    addEntryListener(EntryAddedListener<K, V>(listener), includeValue)