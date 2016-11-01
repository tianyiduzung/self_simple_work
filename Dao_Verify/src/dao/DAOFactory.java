package dao;
public class DAOFactory {
	public static IEmpDAO getIEmpDAOInstance() throws Exception{
		return new EmpDAOProxy() ;
	}
}
