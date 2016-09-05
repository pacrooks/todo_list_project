public class RemoteCategoryIndex extends CategoryIndex {

  public RemoteCategoryIndex() {
    super();
  }

  public Category getCategory(Integer i) {
    RemoteCategory cat = index.get(i);
    if (cat != null) return cat;
    cat = new RemoteCategory(i);
    if (!cat.isEmpty()) {
      index.add(i, cat);
      return cat;
    }
    return null;
  }

  public void fetch() {
    String url = "/categories";
    GetRequest fetchRequest = new GetRequest(url);
    try {
      fetchRequest.sendRequest();
    } catch (Exception e) {
      // Do something
    }
    int[] indices = request.receiveIndexArray();
    for (int i = 0; i < indices.length; i++) {
      index.add(indices[i], null);
    }
  }
}