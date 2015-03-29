package fr.jcontrol.java.javaparser;

import japa.parser.ASTHelper;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.ModifierSet;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.body.VariableDeclaratorId;
import japa.parser.ast.expr.IntegerLiteralExpr;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.expr.StringLiteralExpr;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.ExpressionStmt;
import japa.parser.ast.stmt.IfStmt;
import japa.parser.ast.stmt.ReturnStmt;
import japa.parser.ast.stmt.Statement;
import japa.parser.ast.type.ClassOrInterfaceType;

import java.util.ArrayList;
import java.util.List;

import fr.jcontrol.java.execute.PrepareQualif;

public class ClassCreator {
	
	private ClassCreator() {
		throw new AssertionError("Don't instantiated this class");
	}

	public static CompilationUnit createCUAdditionDouble(String pakg,
			String name, String methodName, Parent nameMere,
			List<String> methodMere, PrepareQualif q) {
		CompilationUnit cu = new CompilationUnit();

		if (nameMere != null) {
			List<ImportDeclaration> lstImports = new ArrayList<ImportDeclaration>();
			lstImports.add(new ImportDeclaration(new NameExpr(nameMere
					.getFullName()), false, false));
			cu.setImports(lstImports);
		}

		// set the package
		cu.setPackage(new PackageDeclaration(ASTHelper.createNameExpr(pakg)));

		// create the type declaration
		ClassOrInterfaceDeclaration type = new ClassOrInterfaceDeclaration(
				ModifierSet.PUBLIC, false, name);

		if (nameMere != null) {
			List<ClassOrInterfaceType> extendsList = new ArrayList<ClassOrInterfaceType>();
			extendsList.add(new ClassOrInterfaceType(nameMere.getSimpleName()));
			type.setExtends(extendsList);
		}

		ASTHelper.addTypeDeclaration(cu, type);

		List<String> myParametre = new ArrayList<String>();
		myParametre.add("a");
		myParametre.add("b");

		ClassOrInterfaceType cT = new ClassOrInterfaceType("java.lang.String");

		// create a method
		QualifMethod qualifMethodEnCours = q.getLstQ().get(
				q.getNbGeneration() - 1);
		List<Parameter> lstParam = new ArrayList<Parameter>();
		if (qualifMethodEnCours.getLstparameterTypes().size() >= 1) {
			if (qualifMethodEnCours.getLstparameterTypes().get(0)
					.equals(double.class)) {
				lstParam.add(new Parameter(ASTHelper.DOUBLE_TYPE,
						new VariableDeclaratorId(myParametre.get(0))));
			} else if (qualifMethodEnCours.getLstparameterTypes().get(0)
					.equals(String.class)) {
				lstParam.add(new Parameter(cT, new VariableDeclaratorId(
						myParametre.get(0))));
			}
		}

		if (qualifMethodEnCours.getLstparameterTypes().size() >= 2) {
			if (qualifMethodEnCours.getLstparameterTypes().get(1)
					.equals(double.class)) {
				lstParam.add(new Parameter(ASTHelper.DOUBLE_TYPE,
						new VariableDeclaratorId(myParametre.get(1))));
			} else if (qualifMethodEnCours.getLstparameterTypes().get(1)
					.equals(String.class)) {
				lstParam.add(new Parameter(cT, new VariableDeclaratorId(
						myParametre.get(1))));
			}
		}

		MethodDeclaration method = new MethodDeclaration(ModifierSet.PUBLIC,
				cT, methodName, lstParam);
		method.setModifiers(ModifierSet.addModifier(method.getModifiers(),
				ModifierSet.STATIC));
		ASTHelper.addMember(type, method);

		// add a body to the method
		BlockStmt block = new BlockStmt();
		method.setBody(block);

		IntegerLiteralExpr b = null;

		if (methodMere == null || methodMere.isEmpty()) {
			b = new IntegerLiteralExpr(
					RandomCustom.getExpressionString(myParametre));
			ReturnStmt r = new ReturnStmt(b);
			ASTHelper.addStmt(block, r);
		} else {
			double rand = Math.random();
			if (rand < 0.1) {
				b = new IntegerLiteralExpr(
						RandomCustom.getExpressionString(myParametre));
				ReturnStmt r = new ReturnStmt(b);
				ASTHelper.addStmt(block, r);
			} else if (rand < 0.9) {
				List<String> myArgs = new ArrayList<String>();
				myArgs.addAll(myParametre);
				myArgs.add(methodMere.get((int) (Math.random() * methodMere
						.size())) + "( a, b ) ");
				b = new IntegerLiteralExpr(
						RandomCustom.getExpressionString(myArgs));
				ReturnStmt r = new ReturnStmt(b);
				ASTHelper.addStmt(block, r);
			} else {

				BlockStmt block2 = new BlockStmt();
				IntegerLiteralExpr b2 = new IntegerLiteralExpr(
						RandomCustom.getExpressionString(myParametre));
				ReturnStmt r2 = new ReturnStmt(b2);
				List<Statement> lst2 = new ArrayList<Statement>();
				lst2.add(r2);
				block2.setStmts(lst2);

				BlockStmt block3 = new BlockStmt();
				IntegerLiteralExpr b3 = new IntegerLiteralExpr(
						RandomCustom.getExpressionString(myParametre));
				ReturnStmt r3 = new ReturnStmt(b3);
				List<Statement> lst3 = new ArrayList<Statement>();
				lst3.add(r3);
				block3.setStmts(lst3);

				IfStmt i = new IfStmt(
						new IntegerLiteralExpr(RandomCustom
								.getExpressionBooleen(new ArrayList<String>())),
						block2, block3);
				ASTHelper.addStmt(block, i);
			}
		}

		return cu;
	}

	public static boolean importExist(List<ImportDeclaration> lstImports,
			String fullNameMere) {
		for (ImportDeclaration i : lstImports) {
			if (i.getName().toString().equals(fullNameMere)) {
				return true;
			}
		}
		return false;
	}

	public static boolean extendsExist(TypeDeclaration type, String fullNameMere) {
		String mere = getExtends(type);
		if (mere != null && mere.equals(fullNameMere)) {
			return true;
		}
		return false;
	}

	public static boolean constructorExist(TypeDeclaration type) {
		List<BodyDeclaration> lstMembers = type.getMembers();
		if (lstMembers != null) {
			for (BodyDeclaration b : lstMembers) {
				if (b instanceof ConstructorDeclaration) {
					return true;
				}
			}
		}
		return false;
	}

	private static String getExtends(TypeDeclaration type) {
		if (type instanceof ClassOrInterfaceDeclaration) {
			ClassOrInterfaceDeclaration cd = (ClassOrInterfaceDeclaration) type;
			if (!cd.isInterface()) {
				List<ClassOrInterfaceType> extendsList = cd.getExtends();
				if (extendsList != null) {
					for (ClassOrInterfaceType cCI : extendsList) {
						return cCI.getName();
					}
				}
			}
		}
		return null;
	}

	public static String getExtends(CompilationUnit cu)  {
		List<TypeDeclaration> lstTypeDeclaration = cu.getTypes();
		if (lstTypeDeclaration.size() > 1) {
			throw new IllegalArgumentException("one type by class");
		}
		TypeDeclaration type = lstTypeDeclaration.get(0);
		return getExtends(type);
	}

	public static void addFieldDeclaration(CompilationUnit cu,
			String fullAttributClass, String attibutClass, String variableName)
			 {
		checkImports(cu, fullAttributClass);

		List<TypeDeclaration> lstTypeDeclaration = cu.getTypes();
		if (lstTypeDeclaration.size() > 1) {
			throw new IllegalArgumentException("one type by class");
		}
		TypeDeclaration type = lstTypeDeclaration.get(0);
		List<BodyDeclaration> lstMembers = type.getMembers();
		if (lstMembers == null) {
			lstMembers = new ArrayList<BodyDeclaration>();
			type.setMembers(lstMembers);
		}
		ClassOrInterfaceType cT = new ClassOrInterfaceType(attibutClass);
		FieldDeclaration f = new FieldDeclaration(ModifierSet.PUBLIC, cT,
				new VariableDeclarator(new VariableDeclaratorId(variableName)));
		lstMembers.add(f);
	}

	public static void addFieldDeclarationGeneric(CompilationUnit cu,
			String fullAttributClass, String attibutClass, String variableName,
			List<String> genericImport, String genericName) {
		checkImports(cu, fullAttributClass);
		if (genericImport != null) {
			for (String s : genericImport) {
				checkImports(cu, s);
			}
		}
		List<TypeDeclaration> lstTypeDeclaration = cu.getTypes();
		if (lstTypeDeclaration.size() > 1) {
			throw new IllegalArgumentException("one type by class");
		}
		TypeDeclaration type = lstTypeDeclaration.get(0);
		List<BodyDeclaration> lstMembers = type.getMembers();
		if (lstMembers == null) {
			lstMembers = new ArrayList<BodyDeclaration>();
			type.setMembers(lstMembers);
		}
		ClassOrInterfaceType cT = new ClassOrInterfaceType(genericName + "<"
				+ attibutClass + ">");
		FieldDeclaration f = new FieldDeclaration(ModifierSet.PUBLIC, cT,
				new VariableDeclarator(new VariableDeclaratorId(variableName),
						new NameExpr("new ArrayList<" + attibutClass + ">()")));
		lstMembers.add(f);
	}

	public static void addSub(CompilationUnit cu, String getFilles,
			String fille) {
		List<TypeDeclaration> lstTypeDeclaration = cu.getTypes();
		if (lstTypeDeclaration.size() > 1) {
			throw new IllegalArgumentException("one type by class");
		}
		TypeDeclaration type = lstTypeDeclaration.get(0);
		List<BodyDeclaration> lstMembers = type.getMembers();
		if (lstMembers != null) {
			for (BodyDeclaration b : lstMembers) {
				if (b instanceof MethodDeclaration) {
					MethodDeclaration m = (MethodDeclaration) b;
					if (m.getName().equals(getFilles)) {
						BlockStmt block = m.getBody();
						List<Statement> lstStats = block.getStmts();
						lstStats.add(lstStats.size() - 1, new ExpressionStmt(
								new NameExpr("lst.add("
										+ new StringLiteralExpr(fille) + ")")));
					}
				}
			}
		}
	}

	public static void updateStaticMethod(CompilationUnit cu,
			String methodName, String paramClass, String paramName) {
		checkImports(cu, paramClass);

		List<TypeDeclaration> lstTypeDeclaration = cu.getTypes();
		if (lstTypeDeclaration.size() > 1) {
			throw new IllegalArgumentException("one type by class");
		}
		TypeDeclaration type = lstTypeDeclaration.get(0);
		ClassOrInterfaceType cT = new ClassOrInterfaceType(paramClass);
		List<BodyDeclaration> lstMembers = type.getMembers();
		if (lstMembers != null) {
			for (BodyDeclaration b : lstMembers) {
				if (b instanceof MethodDeclaration) {
					MethodDeclaration m = (MethodDeclaration) b;
					if (m.getName().equals(methodName)) {
						if (m.getParameters() == null) {
							m.setParameters(new ArrayList<Parameter>());
						}
						m.getParameters().add(
								new Parameter(cT, new VariableDeclaratorId(
										paramName)));
					}
				}
			}
		}
	}

	public static void addStaticMethod(CompilationUnit cu, String methodName) {

		MethodDeclaration method = new MethodDeclaration(ModifierSet.PUBLIC,
				ASTHelper.VOID_TYPE, methodName, null);
		method.setModifiers(ModifierSet.addModifier(method.getModifiers(),
				ModifierSet.STATIC));
		ASTHelper.addMember(cu.getTypes().get(0), method);

		// add a body to the method
		BlockStmt block = new BlockStmt();
		method.setBody(block);

	}

	public static void addElementListInConstructor(CompilationUnit cu,
			String fullAttributClass, String fieldValueClass,
			String fieldValue, String fieldName) {
		checkImports(cu, fullAttributClass);
		checkImports(cu, fieldValueClass);

		List<TypeDeclaration> lstTypeDeclaration = cu.getTypes();
		if (lstTypeDeclaration.size() > 1) {
			throw new IllegalArgumentException("one type by class");
		}
		TypeDeclaration type = lstTypeDeclaration.get(0);
		if (!constructorExist(type)) {
			ConstructorDeclaration c = new ConstructorDeclaration(
					ModifierSet.PUBLIC, type.getName());
			BlockStmt block = new BlockStmt();
			List<Statement> lstStats = block.getStmts();
			if (lstStats == null) {
				lstStats = new ArrayList<Statement>();
				block.setStmts(lstStats);
			}
			lstStats.add(lstStats.size(), new ExpressionStmt(new NameExpr(
					"this." + fieldName + ".add(new "
							+ new NameExpr(fieldValue) + "())")));
			c.setBlock(block);
			ASTHelper.addMember(type, c);
		} else {
			List<BodyDeclaration> lstMembers = type.getMembers();
			if (lstMembers != null) {
				for (BodyDeclaration b : lstMembers) {
					if (b instanceof ConstructorDeclaration) {
						ConstructorDeclaration c = (ConstructorDeclaration) b;
						BlockStmt block = c.getBlock();
						List<Statement> lstStats = block.getStmts();
						if (lstStats == null) {
							lstStats = new ArrayList<Statement>();
							block.setStmts(lstStats);
						}
						lstStats.add(lstStats.size(), new ExpressionStmt(
								new NameExpr("this." + fieldName + ".add(new "
										+ new NameExpr(fieldValue) + "())")));
					}
				}
			}
		}
	}

	private static void checkImports(CompilationUnit cu, String newImport) {
		List<ImportDeclaration> lstImports = cu.getImports();
		if (lstImports == null) {
			lstImports = new ArrayList<ImportDeclaration>();
			cu.setImports(lstImports);
		}
		if (!importExist(lstImports, newImport)) {
			lstImports.add(new ImportDeclaration(new NameExpr(newImport),
					false, false));
		}
	}

	public static void modifyFieldInConstructor(CompilationUnit cu,
			String fullAttributClass, String fieldValueClass,
			String fieldValue, String fieldName) {
		checkImports(cu, fullAttributClass);
		checkImports(cu, fieldValueClass);

		List<TypeDeclaration> lstTypeDeclaration = cu.getTypes();
		if (lstTypeDeclaration.size() > 1) {
			throw new IllegalArgumentException("one type by class");
		}
		TypeDeclaration type = lstTypeDeclaration.get(0);
		if (!constructorExist(type)) {
			ConstructorDeclaration c = new ConstructorDeclaration(
					ModifierSet.PUBLIC, type.getName());
			BlockStmt block = new BlockStmt();
			List<Statement> lstStats = block.getStmts();
			if (lstStats == null) {
				lstStats = new ArrayList<Statement>();
				block.setStmts(lstStats);
			}
			lstStats.add(0, new ExpressionStmt(new NameExpr("this." + fieldName
					+ " = new " + new NameExpr(fieldValue) + "()")));
			c.setBlock(block);
			ASTHelper.addMember(type, c);
		} else {
			List<BodyDeclaration> lstMembers = type.getMembers();
			if (lstMembers != null) {
				for (BodyDeclaration b : lstMembers) {
					if (b instanceof ConstructorDeclaration) {
						ConstructorDeclaration c = (ConstructorDeclaration) b;
						BlockStmt block = c.getBlock();
						List<Statement> lstStats = block.getStmts();
						if (lstStats == null) {
							lstStats = new ArrayList<Statement>();
							block.setStmts(lstStats);
						}
						lstStats.add(0, new ExpressionStmt(new NameExpr("this."
								+ fieldName + " = new "
								+ new NameExpr(fieldValue) + "()")));
					}
				}
			}
		}

	}

	public static void modifyParent(CompilationUnit cu, Parent nameParent) {
		if (nameParent != null) {
			checkImports(cu, nameParent.getFullName());
			List<TypeDeclaration> lstTypeDeclaration = cu.getTypes();
			if (lstTypeDeclaration.size() > 1) {
				throw new IllegalArgumentException("un seul type par classe");
			}
			TypeDeclaration type = lstTypeDeclaration.get(0);
			if (!extendsExist(type, nameParent.getSimpleName()) && type instanceof ClassOrInterfaceDeclaration) {				
				ClassOrInterfaceDeclaration typeCI = (ClassOrInterfaceDeclaration) type;
				List<ClassOrInterfaceType> extendsList = typeCI
						.getExtends();
				if (extendsList == null) {
					extendsList = new ArrayList<ClassOrInterfaceType>();
					typeCI.setExtends(extendsList);
				}
				extendsList.add(new ClassOrInterfaceType(nameParent
						.getSimpleName()));				
			}
		}
	}

	public static String makeIfMethod(String condition, String alors) {
		BlockStmt block2 = new BlockStmt();
		IntegerLiteralExpr b2 = new IntegerLiteralExpr(alors);
		ReturnStmt r2 = new ReturnStmt(b2);
		List<Statement> lst2 = new ArrayList<Statement>();
		lst2.add(r2);
		block2.setStmts(lst2);
		IfStmt i = new IfStmt(new IntegerLiteralExpr(condition), block2, null);

		return i.toString();
	}

	public static CompilationUnit addPriseDecision(CompilationUnit c,
			String methodName) {
		List<String> myParametre = new ArrayList<String>();
		myParametre.add("a");

		ClassOrInterfaceType cT = new ClassOrInterfaceType("java.lang.String");

		List<Parameter> lstParam = new ArrayList<Parameter>();
		lstParam.add(new Parameter(cT, new VariableDeclaratorId(myParametre
				.get(0))));

		MethodDeclaration method = new MethodDeclaration(ModifierSet.PUBLIC,
				cT, methodName, lstParam);
		method.setModifiers(ModifierSet.addModifier(method.getModifiers(),
				ModifierSet.STATIC));

		ASTHelper.addMember(c.getTypes().get(0), method);

		// add a body to the method
		BlockStmt block = new BlockStmt();
		method.setBody(block);
		StringLiteralExpr lexpr = new StringLiteralExpr("a");
		ReturnStmt r3 = new ReturnStmt(lexpr);
		List<Statement> lst3 = new ArrayList<Statement>();
		lst3.add(r3);
		block.setStmts(lst3);

		return c;
	}
	
	public static CompilationUnit createCU(String pakg, String name,
			Parent nameMere, String getFilles) {
		CompilationUnit cu = new CompilationUnit();
		List<ImportDeclaration> lstImports = new ArrayList<ImportDeclaration>();
		if (nameMere != null) {
			lstImports.add(new ImportDeclaration(new NameExpr(nameMere
					.getFullName()), false, false));
		}
		lstImports.add(new ImportDeclaration(
				new NameExpr("java.util.ArrayList"), false, false));
		lstImports.add(new ImportDeclaration(new NameExpr("java.util.List"),
				false, false));

		cu.setImports(lstImports);

		// set the package
		cu.setPackage(new PackageDeclaration(ASTHelper.createNameExpr(pakg)));

		// create the type declaration
		ClassOrInterfaceDeclaration type = new ClassOrInterfaceDeclaration(
				ModifierSet.PUBLIC, false, name);

		ClassOrInterfaceType cT = new ClassOrInterfaceType("List<String>");
		MethodDeclaration method = new MethodDeclaration(ModifierSet.PUBLIC,
				cT, getFilles, null);
		method.setModifiers(ModifierSet.addModifier(method.getModifiers(),
				ModifierSet.STATIC));
		BlockStmt block = new BlockStmt();
		method.setBody(block);
		NameExpr str = new NameExpr(
				"List<String> lst = new ArrayList<String>()");
		ASTHelper.addStmt(block, str);
		ReturnStmt r = new ReturnStmt(new NameExpr("lst"));
		ASTHelper.addStmt(block, r);
		ASTHelper.addMember(type, method);

		if (nameMere != null) {
			List<ClassOrInterfaceType> extendsList = new ArrayList<ClassOrInterfaceType>();
			extendsList.add(new ClassOrInterfaceType(nameMere.getSimpleName()));
			type.setExtends(extendsList);
		}

		ASTHelper.addTypeDeclaration(cu, type);

		return cu;
	}

}
