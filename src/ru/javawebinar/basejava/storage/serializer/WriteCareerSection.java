package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.AbstractSection;
import ru.javawebinar.basejava.model.Career;
import ru.javawebinar.basejava.model.CareerSection;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class WriteCareerSection implements WriteSection {

    @Override
    public void writeSection(DataOutputStream dos, AbstractSection section) {
        try {
            List<Career> element = ((CareerSection) section).getElement();
            dos.writeInt(element.size());
            for (Career list : element) {
                dos.writeUTF(list.getTitle());
                String url = list.getUrl();
                dos.writeUTF(url != null ? url : "");
                List<Career.Position> positions = list.getPositions();
                dos.writeInt(positions.size());
                for (Career.Position position : positions) {
                    dos.writeUTF(position.getPosition());
                    dos.writeUTF(position.getStartDate().toString());
                    dos.writeUTF(position.getEndDate().toString());
                    String description = position.getDescription();
                    dos.writeUTF(description != null ? description : "");
                }
            }
        } catch (IOException e) {
            throw new StorageException("Can't write data", e);
        }
    }
}