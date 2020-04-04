package utilites;
public class CreateQuery
{
	private String	query;
	private int		columns_number;

	public CreateQuery(String query)
	{
		this.query = query;
		String into_skob = query.substring(query.indexOf('(') + 1, query.indexOf(')'));
		columns_number = into_skob.split(",").length;
	}

	public String createVals(int rows)
	{
		String z1 = "";
		String rez = "";
		for (int i = 0; i < rows; i++)
		{
			String row = "(";
			String z = "";
			for (int j = 0; j < columns_number; j++)
			{
				row += z + "?";
				z = ",";
			}
			row += ")";
			rez += z1 + row;
			z1 = ",\n";
		}
		rez += ";";
		return query + rez;
	}

	public int getColNunbers()
	{
		return columns_number;
	}
}