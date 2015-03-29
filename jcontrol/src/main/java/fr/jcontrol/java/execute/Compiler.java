package fr.jcontrol.java.execute;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.jcontrol.java.javaparser.WriteReader;

 
public class Compiler {
	
	private final static Logger LOG = LoggerFactory.getLogger(Compiler.class);
	
	
	private String target;
	private String targetJava;
	private MyClassLoader loader;
	private JavaCompiler compiler;
	private StandardJavaFileManager stdFileManager;
	private List<String> optionList;
	   
	
	public Compiler(String cible, String cibleJava){
		this.target = cible;
		this.targetJava = cibleJava;
		
		WriteReader.makeDir(cibleJava);
		WriteReader.makeDir(cible);
		 
		 compiler = ToolProvider.getSystemJavaCompiler();     
		
		 /**
         * Retrieving the standard file manager from compiler object, which is used to provide
         * basic building block for customizing how a compiler reads and writes to files.
         *
         * The same file manager can be reopened for another compiler task.
         * Thus we reduce the overhead of scanning through file system and jar files each time
         */
	    stdFileManager = compiler.getStandardFileManager(null, Locale.getDefault(), null); 
	     
	    /*Prepare any compilation options to be used during compilation*/
        //In this example, we are asking the compiler to place the output files under "target" folder.
	    optionList = new ArrayList<String>();
        optionList.add("-d");
        optionList.add(cible);
        // set compiler's classpath to be same as the runtime's
        optionList.add("-classpath");
        optionList.add(cible);   
	}
	
	public void newClassLoader(){
		loader = new MyClassLoader(target);
	}	
	
 
    /**
     * Does the required object initialization and compilation.
     */
    public void doCompilation(String name, String sourceCode) {  
    	
    	writeFile(name, sourceCode);    	
    	
    	 /*Creating dynamic java source code file object*/  
    	SimpleJavaFileObject sjfo = new DynamicJavaSourceCodeObject(name, sourceCode);
           
        List<SimpleJavaFileObject> compilationUnits = new ArrayList<SimpleJavaFileObject>();
        compilationUnits.add(sjfo);       
 
        /*Create a diagnostic controller, which holds the compilation problems*/
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();        
        
        /*Create a compilation task from compiler by passing in the required input objects prepared above*/       
        boolean status = compiler.getTask(null, stdFileManager, diagnostics, optionList, null, compilationUnits).call();  
        
        if (!status){//If compilation error occurs
            /*Iterate through each compilation problem and print it*/
            for (Diagnostic diagnostic : diagnostics.getDiagnostics()){   
            	 throw new IllegalArgumentException("Error on line " + diagnostic.getLineNumber() + " in " + diagnostic);
            }
        }        
    }
    
    public void writeFile(String name, String sourceCode){
    	 String[] b = name.split("\\.");
    	 String str = targetJava + "/" + name.replaceAll("\\.", "/") + ".java";
    	 StringBuilder strB = new StringBuilder();	
		 strB.append(targetJava);			 
		 WriteReader.subMakeDir(b, strB, b.length - 1);			
		 
		 try (FileWriter fstream = new FileWriter(str); BufferedWriter out = new BufferedWriter(fstream); ){			 
			  // Create file 			  
			  out.write(sourceCode);
			  LOG.debug("writeFile {}", name);			 
		  } catch (Exception e){
			  LOG.error("writeFile", e);
		  }			 
	}
    
    public String[] listerRepertoire(String pkg, final String begin){	
    	String pkgRe = pkg.replaceAll("\\.", "/");
    	File repertoire = new File(targetJava + "/" + pkgRe);
		FilenameFilter javaFilter = new FilenameFilter() { 
			public boolean accept(File arg0, String arg1) { 
				return arg1.matches("^" + begin + "[\\d]+\\.java"); 
			} 
		}; 		
		return repertoire.list(javaFilter); 		
	}
	
    
    public  String getPathClassJava(String fullName){
    	String reformat = fullName.replaceAll("\\.", "/");
    	return targetJava + "/" + reformat + ".java";    	
    }
    
    public boolean deleteFileClassAndJava(Object o1){
    	
    	boolean delete1 = false;
    	String name1 = o1.getClass().getName().toString();
    	String reformat = name1.replaceAll("\\.", "/");
    	String str = targetJava + "/" + reformat + ".java";
    	File myFile = new File(str);     	
		boolean b1 = myFile.delete(); 		
		if(b1){
			delete1 = true;		
		}
			
		
		boolean delete2 = false;
		String str2 = target + "/" + reformat + ".class";
		File myFile2 = new File(str2); 
		boolean b2 = myFile2.delete(); 
		if(b2){	
			delete2 = true;			
		}
		
		return delete1 && delete2;
    }

	public MyClassLoader getLoader() {
		return loader;
	}
}
 

