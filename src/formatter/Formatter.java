package formatter;

import api.data.APIDataObject;

public interface Formatter {

	void add(APIDataObject obj);

	void setFile(String file);

	void setWriteAmount(int amount);
}
