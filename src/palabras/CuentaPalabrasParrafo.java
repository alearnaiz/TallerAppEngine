package palabras;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class CuentaPalabrasParrafo {

            //cadenas de entrada junto con el número de línea
    private ArrayList<Parrafo> cadenas;
            //listga de cadenas significativas.
    private ArrayList<String> significativas;
            //estructura de datos final
    private SortedMap<String, SortedMap<Integer, SortedSet<Integer>>> mapa;
    private int linea;
    
    public CuentaPalabrasParrafo() {
        mapa=new TreeMap();
        cadenas=new ArrayList();
        significativas=new ArrayList();
        linea=1;
    }
    
    public void agregarLínea(String cad){
        Parrafo p=new Parrafo(cad, linea);
        linea++;
        cadenas.add(p);
    }
    
    public void resolver(String delimitadores, List<String> noSignificativas){

        Parrafo p;
        Iterator it=cadenas.iterator(); //cadenas introducidas

                //tratar lineas introducidas
        while(it.hasNext()){
            p=(Parrafo)it.next();            
                    //tratar palabras de cada línea.
            tratar_linea(p, delimitadores, noSignificativas);
        }
        System.out.println(mapa);
    }
    
    private void tratar_linea(Parrafo p, String delimitadores, List<String> noSignificativas){
        StringTokenizer aux;
        TreeMap mf=new TreeMap();
        TreeSet mc=new TreeSet();
        String cad;
        int colum=1;
        
        aux=new StringTokenizer(p.cadena, delimitadores);
        linea=p.linea;
                //tratar palabras de cada línea.
        while(aux.hasMoreTokens()){
            cad=aux.nextToken(); 
            if (!busca(cad, noSignificativas)){ //es cadena siginificativa
                cad=cad.toLowerCase(); //pasomos a minúsculas todas las cadenas
        
                        //lista de lineas asociadas a la cadena
                mf=(TreeMap)mapa.get(cad);                   
                if (mf!=null){ //cadena está en el mapa
                            //lista de columnas asociadas a la linea actual
                        mc=(TreeSet)mf.get((Integer)linea);
                        if(mc==null){                          
                            mc=new TreeSet();  
                        }
                        
                }else{  //cadena nueva
                    mf=new TreeMap();
                    mc=new TreeSet();
                }  
                mc.add((Integer)colum);
                mf.put(linea,mc);
                mapa.put(cad, mf); 
                
                significativas.add(cad);
            }
            colum++;
        }
        colum=1;
    }
    public void mostrarEnConsola(){
        eliminar_repeticiones();
                //mostramos todas las lineas introducidas por el usuario
        for (int i=0; i<cadenas.size(); i++){
            System.out.println(cadenas.get(i).cadena);
        }
                //mostramos palabra, linea y columnas en las que aparece
        for (int i=0; i<significativas.size(); i++){
            String cad=(String)significativas.get(i);
            imprimirEnConsola(cad);
        }
        
   
    }
    
    private void eliminar_repeticiones(){
                //eliminamos
        for(int i=0; i<(significativas.size()-1); i++){
            int j=i+1;
            while( j<significativas.size()){
                if(significativas.get(i).compareTo(significativas.get(j))==0){
                    significativas.remove(j);
                }
                else
                    j++;
            }
        }  
                //ordenamos
        String aux;
         for(int i=0; i<(significativas.size()-1); i++){
             for(int j=i+1; j<significativas.size(); j++){
                 if(significativas.get(i).compareTo(significativas.get(j))>0){
                     aux=significativas.get(i);
                     significativas.set(i,significativas.get(j));
                     significativas.set(j,aux);
                 }
             }
         } 
    }
    
    
    private void imprimirEnConsola(String cad){
        TreeMap mf; //filas
        TreeSet mc; //columnas
        Iterator it1;
            //obtener por cada palabra significativa el mapa de línea y columnas
        String aux=cad;
        mf=(TreeMap)(mapa.get(cad));
        if (mf!=null){
                //tratar cada línea en la que aparece la palabra
            for (int i=0; i<linea;i++){
                mc=(TreeSet)(mf.get((Integer)(i+1)));
                if(mc!=null){
                    it1=mc.iterator();
                    aux=aux+"\n\t"+(i+1)+" ";
                        //imprimir las columnas
                    while (it1.hasNext())
                        aux=aux+(Integer)it1.next()+".";
                }   
            }
            System.out.println(aux);
        }
    }
    private boolean busca (String cad, List<String> noSignificativas){
        Iterator it=noSignificativas.iterator();
        boolean enc=false;
        while(it.hasNext() && !enc){
            String cad1=(String)it.next();
            enc=((cad1.toLowerCase()).compareTo(cad.toLowerCase())==0);
            //enc=(cad1.compareTo(cad)==0);
        }
        return(enc);
    }
    
    
    class Parrafo{
        public String cadena;
        public int linea;
        public Parrafo(String cad, int l){
            cadena=cad;
            linea=l;
        }
    }
}

