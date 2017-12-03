/*
 * Copyright 2014, Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser
 *
 * Developed for use with the book:
 *
 *    Data Structures and Algorithms in Java, Sixth Edition
 *    Michael T. Goodrich, Roberto Tamassia, and Michael H. Goldwasser
 *    John Wiley & Sons, 2014
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.util.Set;
import java.util.HashSet;

/**
 * A collection of graph algorithms.
 */
public class GraphAlgorithms {

  /**
   * Computes shortest-path distances from src vertex to all reachable vertices of g.
   *
   * This implementation uses Dijkstra's algorithm.
   *
   * The edge's element is assumed to be its integral weight.
   */
  public static <V> Map<Vertex<V>, Integer>
  shortestPathLengths(Graph<V,Integer> g, Vertex<V> src) {
    // d.get(v) is upper bound on distance from src to v
    Map<Vertex<V>, Integer> d = new ProbeHashMap<>();
    // map reachable v to its d value
    Map<Vertex<V>, Integer> cloud = new ProbeHashMap<>();
    // pq will have vertices as elements, with d.get(v) as key
    AdaptablePriorityQueue<Integer, Vertex<V>> pq;
    pq = new HeapAdaptablePriorityQueue<>();
    // maps from vertex to its pq locator
    Map<Vertex<V>, Entry<Integer,Vertex<V>>> pqTokens;
    pqTokens = new ProbeHashMap<>();

    // for each vertex v of the graph, add an entry to the priority queue, with
    // the source having distance 0 and all others having infinite distance
    for (Vertex<V> v : g.vertices()) {
      if (v == src)
        d.put(v,0);
      else
        d.put(v, Integer.MAX_VALUE);
      pqTokens.put(v, pq.insert(d.get(v), v));       // save entry for future updates
    }
    // now begin adding reachable vertices to the cloud
    while (!pq.isEmpty()) {
      Entry<Integer, Vertex<V>> entry = pq.removeMin();
      int key = entry.getKey();
      Vertex<V> u = entry.getValue();
      cloud.put(u, key);                             // this is actual distance to u
      pqTokens.remove(u);                            // u is no longer in pq
      for (Edge<Integer> e : g.outgoingEdges(u)) {
        Vertex<V> v = g.opposite(u,e);
        if (cloud.get(v) == null) {
          // perform relaxation step on edge (u,v)
          int wgt = e.getElement();
          if (d.get(u) + wgt < d.get(v)) {              // better path to v?
            d.put(v, d.get(u) + wgt);                   // update the distance
            pq.replaceKey(pqTokens.get(v), d.get(v));   // update the pq entry
          }
        }
      }
    }
    return cloud;         // this only includes reachable vertices
  }

  /**
   * Reconstructs a shortest-path tree rooted at vertex s, given distance map d.
   * The tree is represented as a map from each reachable vertex v (other than s)
   * to the edge e = (u,v) that is used to reach v from its parent u in the tree.
   */
  public static <V> Map<Vertex<V>,Edge<Integer>>
  spTree(Graph<V,Integer> g, Vertex<V> s, Map<Vertex<V>,Integer> d) {
    Map<Vertex<V>, Edge<Integer>> tree = new ProbeHashMap<>();
    for (Vertex<V> v : d.keySet())
      if (v != s)
        for (Edge<Integer> e : g.incomingEdges(v)) {   // consider INCOMING edges
          Vertex<V> u = g.opposite(v, e);
          int wgt = e.getElement();
          if (d.get(v) == d.get(u) + wgt)
            tree.put(v, e);                            // edge is is used to reach v
        }
    return tree;
  }


}
