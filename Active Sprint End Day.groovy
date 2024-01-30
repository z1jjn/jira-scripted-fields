// Please disregard the error.
// Import library
import com.atlassian.jira.component.ComponentAccessor

// Disable cache
enableCache = {-> false}

// Get the value of the sprint field
def sprint = issue.getCustomFieldValue(ComponentAccessor.getCustomFieldManager().getCustomFieldObjectByName("Sprint"))

// If the latest sprint's state is active
if (sprint != null && sprint.state.last()?.toString() == "ACTIVE")
	{ 
        // Get the end date of the latest sprint
		return sprint.endDate.last().toDate()
	}
else
    {
		return null
    }