// Import libraries
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.config.SubTaskManager
import com.atlassian.jira.issue.CustomFieldManager

// Disable cache
enableCache = {-> false}

// Initialize work weight value
double workWeightValue = 0 

// Initialize class
CustomFieldManager customFieldManager = ComponentAccessor.getComponent(CustomFieldManager)
SubTaskManager subTaskManager = ComponentAccessor.getComponent(SubTaskManager)

//Get each sub-tasks
subTaskManager.getSubTaskObjects(issue).each{ subTask ->
    // For each sub-tasks, get all work weight, and for each work weight value
    customFieldManager.getCustomFieldObjects(subTask).findAll{it.name.equals("Work Weight")}.each{ workWeight ->
        // If the work weight is not null
        if (subTask.getCustomFieldValue(workWeight) != null)
            {
                // Append the work weight to the total work weight
                workWeightValue += subTask.getCustomFieldValue(workWeight) as Double
            }
    }
}
// Return the final work weight value
return workWeightValue
