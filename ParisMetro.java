import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;

import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;

public class ParisMetro{
	//constructor
	Graph<String, Integer> parisGraph;


	public ParisMetro(String fileName) throws Exception, IOException {
		parisGraph = new AdjacencyMapGraph<String, Integer>(false);
		readMetro(fileName);
	}
	/**
	*taken from lab 7, WeightGraph.java file, //to be modifdy
	*/
	protected void readMetro(String fileName) {
		BufferedReader metroFile = new BufferedReader(new FileReader(fileName));
		boolean flag;
		// Hashtable to store all the verticess
		Hashtable<String, Vertex> vertices = new Hashtable<String, Vertex>();
		//I DONT WHAT TO USE THE TOKENIZER IT SEEMS DUMB FOR WHAT I WANT TO DO
		//actually use tokienzer for after we read the $ sign
		//find something else beforehand to make it gucci gucci
		//could implement something along the lines of 
		// Read the edges and insert
		//i made it switch the the tokiener when the $ flag is set to true
		//this will allow me to recored the weight and shite
		flag=false;
		String line;
		while ((line = metroFile.readLine()) != null) {
			//need to do thingy for the first line in the document
			//store the numbers of egdes and vertices but where and howww

			//checking to see if we have hit the break point in the txt file
			if(line=="$"){
				flag=true;
			}
			//befoe the $ store all numbers and station names in a list
			//so that we can acces the actually name of the station from the
			//map that holds its number value
			if(flag==false){
				StringTokenizer st = new StringTokenizer(line);
				if (st.countTokens() != 2)
					throw new IOException("Incorrect input file at line " + line);
				Integer stationNum = new Integer(st.nextToken());
				String stationName = st.nextToken();
				
			}
			///once we hit the dolla sign gang gang
			if(flag==true){
				StringTokenizer st = new StringTokenizer(line);
				if (st.countTokens() != 3)
					throw new IOException("Incorrect input file at line " + line);
				String source = st.nextToken();
				String dest = st.nextToken();
				Integer weight = new Integer(st.nextToken());
				//changes weight to 90 for walking wieght
				if (weight==-1){
					weight=90;
				}
				
				Vertex<String> sv = vertices.get(source);
				if (sv == null) {
					// Source vertex not in graph -- insert
					sv = parisGraph.insertVertex(source);
					vertices.put(source, sv);
				}
				Vertex<String> dv = vertices.get(dest);
				if (dv == null) {
					// Destination vertex not in graph -- insert
					dv = parisGraph.insertVertex(dest);
					vertices.put(dest, dv);
				}
				// check if edge is already in graph
				if (parisGraph.getEdge(sv, dv) == null) {
					// edge not in graph -- add
					//e's element is now the distance between the vertices
					//Modified by Thais Bardini on November 19th, 2017 (tbard069@uottawa.ca)
					Edge<Integer> e = parisGraph.insertEdge(sv, dv, weight);
				}
			}
		}
	}

	//this is a directed graph

	
	/*ALL THREE BELOW 
	void for the moment to explain my thought 
	all 3 methods however will most likely return an array[]
	*/

	/* FINDTRAINLINE
	so i am thinking that for this one we make an adjecney list in another method 
	we will call to it tho, since in the list we will store all the staions one by one
	and also the directioned staion they go to and the time it takes 
	ei. list[0]-> |(0, 238, 41)|(0, 159, 46)| (station from, station too, time/weight)
	then when we are searching for the station line if it starts at zero if will go to 
	the list and take the first item stored there for previous example we would one take the (0, 238,41)
	and with this we would set 238 as the next vertix to check in the same manner
	and we would add ther 41 to a counter that counts the seconds
	all while adding each station grabbed to another list
	we just need to find a way to be able to check when we hit the end of the line
	the end on the line is hit in either two conditions, when the last staion loops back to the start
	or it visits a staion that has already been visit, or if it has no other staion that it could go to
	*/
	public void findTrainLine(String n1){

		}
	/*SHORTESTPATH

	*/
	public void shortestPath(String n1, String n2){

	}
	/*BROKENSHARTESTPATH

	*/
	public void brokenShortestPath(Srting n1, String n2, String n3){

	}

	public static void main(String[] args) {
		//creates and stores the graph here and we can peform stuff from here
		ParisGraph pGraph = new parisGraph("metro.txt");

		//2-i
		//given the intial station print all other stations on that line that without switching lines
		//but what is one line is that for us to decide? 
		//or is it when we could walk from a station to another
		//so if it is maybe i keep -1 as the wieght of the graph and only change it in the 3 calls vv
		//needs to call to 
		
		if (args.length==1){
			findTrainLine(args[0]);
		}
		//2-ii
		else if(args.length==2){
			shortestPath(args[0], args[1]);
		}
		//2-iii
		else if(args.length==3){
			brokenShortestPath(args[0],args[1],args[2]);
		}
	}
	public static void main(String[] args) {
		if (argv.length < 1) {
			System.err.println("Usage: java WeightGraph fileName");
			System.exit(-1);
		}
		try {
			WeightGraph sGraph = new WeightGraph(argv[0]);
			sGraph.print();
			// Ask for vertex to start
			System.out.println("Source Vertex for Shortest Path:");
			sGraph.printAllShortestDistances(readVertex());
		} catch (Exception except) {
			System.err.println(except);
			except.printStackTrace();
		}
	}
}