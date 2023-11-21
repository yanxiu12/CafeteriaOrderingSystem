import java.io.*;
import java.util.ArrayList;

public class SerializationOperation implements java.io.Serializable{

    private String filename;

    public SerializationOperation(String filename) {
        this.filename = filename;
    }

    // Serialize a generic object
    public void serializeObject(Object obj) {
        try (ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(filename))) {
            objectOut.writeObject(obj);
            objectOut.close();
        } catch (IOException e) {
            System.out.println("Error serializing object: " + e.getMessage());
        }
    }

    // Deserialize an object
    public Object deserializeObject() {
        Object deserializedObject = null;
        try (ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(filename))) {
            deserializedObject = objectIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error deserializing object: " + e.getMessage());
        }
        return deserializedObject;
    }

    public <T> ArrayList<T> readAllObjects(Class<T> objectType) {
        ArrayList<T> allObjects = new ArrayList<>();
        Object existingData = deserializeObject();
        if (existingData instanceof ArrayList) {
            ArrayList<Object> dataList = (ArrayList<Object>) existingData;
            for (Object obj : dataList) {
                if (objectType.isInstance(obj)) {
                    allObjects.add(objectType.cast(obj));
                }
            }
        }
        return allObjects;
    }

    // Add a new object to the file
    public <T> void addObject(T obj) {
        ArrayList<T> existingData = (ArrayList<T>) readAllObjects(obj.getClass());
        existingData.add(obj);
        serializeObject(existingData);
    }

    // Search for an object based on some criteria
    public <T> ArrayList<T> searchObjects(String searchKey, Class<T> objectType) {
        ArrayList<T> foundObjects = new ArrayList<>();
        Object existingData = deserializeObject();
        if (existingData instanceof ArrayList) {
            ArrayList<Object> dataList = (ArrayList<Object>) existingData;
            for (Object obj : dataList) {
                // Implement search logic based on your object structure
                if (objectType.isInstance(obj)) {
                    // Check if the object is of the specified type
                    String id;
                    if (obj instanceof Customer) {
                        id = ((Customer) obj).getID();
                    } else if (obj instanceof Vendor) {
                        id = ((Vendor) obj).getID();
                    } else if (obj instanceof Runner) {
                        id = ((Runner) obj).getID();
                    } else {
                        // Add additional checks for other types if needed
                        continue; // Skip if not the expected type
                    }

                    // Perform the search based on the provided search key
                    if (id.contains(searchKey)) {
                        foundObjects.add(objectType.cast(obj));
                    }
                }
            }
        }
        return foundObjects;
    }

    // Update an existing object
    public <T> void updateObject(String idToUpdate, T updatedObj) {
        ArrayList<T> existingData = (ArrayList<T>) readAllObjects(updatedObj.getClass());
        for (int i = 0; i < existingData.size(); i++) {
            T obj = existingData.get(i);
            // Replace this condition with your object identification logic
            if (obj.toString().contains(idToUpdate)) {
                existingData.set(i, updatedObj);
                break;
            }
        }
        serializeObject(existingData);
    }

    // Delete an object based on ID or other identification logic
    public <T> void deleteObject(String idToDelete, Class<T> objectType) {
        ArrayList<T> existingData = readAllObjects(objectType);
        existingData.removeIf(obj -> obj.toString().contains(idToDelete));
        serializeObject(existingData);
    }
}
