/*
 * Copyright 2012-2013 by Cloudsoft Corp.
 */
package brooklyn.entity.nosql.cassandra;

import java.util.Set;

import brooklyn.catalog.Catalog;
import brooklyn.config.ConfigKey;
import brooklyn.entity.Entity;
import brooklyn.entity.annotation.Effector;
import brooklyn.entity.basic.ConfigKeys;
import brooklyn.entity.basic.MethodEffector;
import brooklyn.entity.group.DynamicFabric;
import brooklyn.entity.proxying.ImplementedBy;
import brooklyn.event.AttributeSensor;

import com.google.common.collect.Multimap;

/**
 * A fabric of {@link CassandraNode}s, which forms a cluster spanning multiple locations.
 * <p>
 * Each {@link CassandraCluster} child instance is actually just a part of the whole cluster. It consists of the
 * nodes in that single location (which normally corresponds to a "datacenter" in Cassandra terminology).
 */
@Catalog(name="Apache Cassandra Database Fabric", description="Cassandra is a highly scalable, eventually " +
        "consistent, distributed, structured key-value store which provides a ColumnFamily-based data model " +
        "richer than typical key/value systems", iconUrl="classpath:///cassandra-logo.jpeg")
@ImplementedBy(CassandraFabricImpl.class)
public interface CassandraFabric extends DynamicFabric {

    ConfigKey<Integer> INITIAL_QUORUM_SIZE = ConfigKeys.newIntegerConfigKey(
            "fabric.initial.quorumSize",
            "Initial fabric quorum size - number of initial nodes that must have been successfully started " +
            "to report success (if less than 0, then use value of INITIAL_SIZE)",
            -1);

    AttributeSensor<Multimap<String,Entity>> DATACENTER_USAGE = CassandraCluster.DATACENTER_USAGE;

    AttributeSensor<Set<String>> DATACENTERS = CassandraCluster.DATACENTERS;

    AttributeSensor<Set<Entity>> CURRENT_SEEDS = CassandraCluster.CURRENT_SEEDS;

    AttributeSensor<Boolean> HAS_PUBLISHED_SEEDS = CassandraCluster.HAS_PUBLISHED_SEEDS;

    AttributeSensor<String> HOSTNAME = CassandraCluster.HOSTNAME;

    AttributeSensor<Integer> THRIFT_PORT = CassandraCluster.THRIFT_PORT;

    MethodEffector<Void> UPDATE = new MethodEffector<Void>(CassandraFabric.class, "update");

    @Effector(description="Updates the cluster members")
    void update();
}
