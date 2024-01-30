// Initialize libraries
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.RendererManager
import com.atlassian.jira.issue.fields.renderer.IssueRenderContext
import com.atlassian.jira.issue.fields.renderer.wiki.AtlassianWikiRenderer
import com.atlassian.jira.timezone.TimeZoneManager
import java.text.SimpleDateFormat

// Initialize simple date format
SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm aa z")

// Initialize class
def rendererManager = ComponentAccessor.getComponent(RendererManager)
def renderContext = new IssueRenderContext(issue)
def commentManager = ComponentAccessor.commentManager
// Get the last comemnt
def comment = commentManager.getLastComment(issue)

// If a (last) comment exists, return it. Otherwise, return null
if (comment)
	{
        sdf.setTimeZone(ComponentAccessor.getComponent(TimeZoneManager).getLoggedInUserTimeZone())
        comment.authorFullName + " added a comment - " + (sdf.format(comment.getCreated()) as String) + "<br>" + rendererManager.getRenderedContent(AtlassianWikiRenderer.RENDERER_TYPE, comment.body, renderContext)
	}
else
	{
		return null
	}