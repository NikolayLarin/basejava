package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.Career;
import ru.javawebinar.basejava.model.CareerSection;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class CareerSectionWriter implements Writer<CareerSection> {
    @Override
    public void writeSection(DataOutputStream dos, CareerSection section) throws IOException {
        List<Career> element = section.getElement();
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
    }
}