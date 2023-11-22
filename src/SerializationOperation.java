import java.io.*;
import java.util.ArrayList;

public class SerializationOperation implements java.io.Serializable{

    private String filename;

    public SerializationOperation(String filename) {
        this.filename = filename;
    }

    // Serialize a generic object
    public <T> void serializeObject(ArrayList<T> objList) {
        try (ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(filename))) {
            for (T obj : objList) {
                objectOut.writeObject(obj);
            }
        } catch (IOException e) {
            System.out.println("Error serializing object: " + e.getMessage());
        }
    }

    // Deserialize an object
    public <T> ArrayList<T> deserializeObjects() {
        ArrayList<T> deserializedObjects = new ArrayList<>();
        try (ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(filename))) {
            Object obj;
            while ((obj = objectIn.readObject()) != null) {
                deserializedObjects.add((T) obj);
            }
        } catch (EOFException e) {
            // End of file reached
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error deserializing objects: " + e.getMessage());
        }
        return deserializedObjects;
    }

    public <T> ArrayList<T> readAllObjects(Class<T> objectType) {
        ArrayList<T> allObjects = new ArrayList<>();
        ArrayList<T> deserializedObjects = deserializeObjects(); // Using the updated method

        for (T obj : deserializedObjects) {
            if (objectType.isInstance(obj)) {
                allObjects.add(obj);
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
        ArrayList<T> deserializedObjects = deserializeObjects();

        for (T obj : deserializedObjects) {
            if (objectType.isInstance(obj)) {
                // Implement search logic based on your object structure
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
                    foundObjects.add(obj);
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
