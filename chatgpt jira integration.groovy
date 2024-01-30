/*
This is a scripted field with text-multiline output. Please disregard the error.
Please note that this might cost a lot as this will add a custom field for every ticket.
Please modify the code as needed.
*/

import com.atlassian.jira.component.ComponentAccessor
@Grab(group='io.github.http-builder-ng', module='http-builder-ng-core', version='1.0.4')
import groovyx.net.http.HttpBuilder
import com.atlassian.jira.issue.RendererManager
import com.atlassian.jira.issue.fields.renderer.IssueRenderContext
import com.atlassian.jira.issue.fields.renderer.wiki.AtlassianWikiRenderer
import groovy.json.JsonParserType
def customFieldManager = ComponentAccessor.getCustomFieldManager()
def additionalInformation = customFieldManager.getCustomFieldObject("customfield_10033")
String additionalInformationValue = issue.getCustomFieldValue(additionalInformation)
def rendererManager = ComponentAccessor.getComponent(RendererManager)
def renderContext = new IssueRenderContext(issue)
String body = """{"model": "gpt-3.5-turbo","messages": [{"role":"user","content":"Please analyze this request and fill the information format below. You can leave the parts that lack information empty. Fill in the information format as if you are the requester. Information Format Jira/Wiki (Confluence) : Jira Project or Wiki Space : Request Content/Detail : Request purpose : Request: ${additionalInformationValue.toString()}"}]}"""
def json = HttpBuilder.configure {
    request.headers['Authorization'] = "Bearer REPLACE WITH YOUR TOKEN"
    request.uri = "https://api.openai.com"
    request.uri.path = "/v1/chat/completions"
    request.contentType = 'application/json'
    request.body = body
}.post()
def jsonSlurper = new groovy.json.JsonSlurper().setType(JsonParserType.LAX)
def jsonOutput = new groovy.json.JsonOutput()
def result = jsonSlurper.parseText(jsonOutput.toJson(json).toString())
rendererManager.getRenderedContent(AtlassianWikiRenderer.RENDERER_TYPE, result.choices[0].message.content, renderContext)