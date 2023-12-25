package com.aeritt.aerreplacer.config;

import com.aeritt.aerreplacer.model.replacement.ServiceReplacement;
import com.aeritt.aerreplacer.model.replacement.TaskReplacement;
import net.elytrium.serializer.annotations.Comment;
import net.elytrium.serializer.annotations.CommentValue;
import net.elytrium.serializer.language.object.YamlSerializable;

import java.util.ArrayList;
import java.util.List;

public class ReplacementConfig extends YamlSerializable {
    public List<TaskReplacement> tasks = new ArrayList<>();

    @Comment(
            value = {
                    @CommentValue(type = CommentValue.Type.NEW_LINE)
            },
            at = Comment.At.PREPEND
    )
    public List<ServiceReplacement> services = new ArrayList<>();
}
